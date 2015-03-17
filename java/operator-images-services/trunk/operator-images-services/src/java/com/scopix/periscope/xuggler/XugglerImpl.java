package com.scopix.periscope.xuggler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

/**
 * Clase para manipulación de videos con Xuggler
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class XugglerImpl implements Serializable {

    int upperLimit = 0;
    int lowerLimit = 0;
    // Tamaño de los snapshots reducidos
    private final int imgWidth = 129;
    private final int imgHeight = 72;
    private static final long serialVersionUID = -7255044272094265685L;
    private static Logger log = Logger.getLogger(XugglerImpl.class);

    /**
     * Obtiene imágen sprite de un video correspondiente a su número
     *
     * @author carlos polo
     * @version 1.0.0
     * @param inputFilename String
     * @param spriteNum int
     * @since 6.0
     * @return BufferedImage
     * @date 25/03/2013
     */
    @SuppressWarnings("deprecation")
    public BufferedImage getSprite(String inputFilename, int spriteNum) {

        IContainer container = null;
        IStreamCoder videoCoder = null;
        BufferedImage ret = null;
        try {
            List<BufferedImage> snapshots = new ArrayList<BufferedImage>();
            // Make sure that we can actually convert video pixel formats
            if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION)) {
                log.debug("you must install the GPL version of Xuggler");
            }

            // Create a Xuggler container object
            container = IContainer.make();

            // Open up the container
            if (container.open(inputFilename, IContainer.Type.READ, null) < 0) {
                log.debug("could not open file: " + inputFilename);
            } else {
                long durationInSegs = container.getDuration() / 1000000;

                // Query how many streams the call to open found
                int numStreams = container.getNumStreams();

                // Iterate through the streams to find the first video stream
                int videoStreamId = -1;
                for (int i = 0; i < numStreams; i++) {
                    // Find the stream object
                    IStream stream = container.getStream(i);
                    // Get the pre-configured decoder that can decode this stream;
                    IStreamCoder coder = stream.getStreamCoder();

                    if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                        videoStreamId = i;
                        videoCoder = coder;
                        break;
                    }
                }

                // if (videoStreamId == -1) {
                // log.debug("could not find video stream in container: " + inputFilename);
                // }

                // Now we have found the video stream in this file. Let's open up Our decoder so it can do work
                if (videoCoder.open() < 0) {
                    log.debug("could not open video decoder for container: " + inputFilename);
                }

                IVideoResampler resampler = null;
                if (videoCoder != null) {
                    if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
                        // If this stream is not in BGR24, we're going to need to Convert it. The VideoResampler does that for us.
                        resampler = IVideoResampler.make(videoCoder.getWidth(), videoCoder.getHeight(), IPixelFormat.Type.BGR24,
                                videoCoder.getWidth(), videoCoder.getHeight(), videoCoder.getPixelType());
                    }
                }
                // We create a new packet.
                IPacket packet = IPacket.make();

                // Let's check the timeBase of this container
                IRational timeBase = container.getStream(videoStreamId).getTimeBase();
                long oper = (timeBase.getDenominator() / timeBase.getNumerator());
                // Obtiene DTS del primer y segundo keyframe
                Long[] arrKeys = getKeyFramesDts(container, packet, videoStreamId, oper);
                long firstKeyDts = arrKeys[0]; // dts del 1er keyframe
                long secondKeyDts = arrKeys[1]; // dts del 2do keyframe

                long keyDifference = secondKeyDts - firstKeyDts;

                // Calcula el número de sprites de acuerdo a la duración
                Double totalSprites = Double.valueOf(new Double(durationInSegs) / new Double(25));

                // Valida si tiene decimales para sumar 1
                if (totalSprites % 1.0 > 0) {
                    totalSprites += 1;
                }
                upperLimit = spriteNum * 25;
                lowerLimit = upperLimit - 25;
                // With the stream timebase we can calculate the timestamp
                Double timeStampOffset = oper * Double.valueOf(lowerLimit);

                // Valida si el tiempo solicitado está antes de la ubicación del 1er
                // keyframe
                if (timeStampOffset < firstKeyDts) {
                    // move the seek head backwards
                    container.seekKeyFrame(-1, Long.MIN_VALUE, 0, Long.MAX_VALUE, IContainer.SEEK_FLAG_BACKWARDS);
                } else {
                    // Se ubica en el keyframe inmediatamente anterior de acuerdo a la diferencia proporcional entre keys
                    // PARAMS: int streamIndex, long minTimeStamp, long targetTimeStamp, long maxTimeStamp, int flags
                    container.seekKeyFrame(videoStreamId, (timeStampOffset.longValue() - keyDifference),
                            timeStampOffset.longValue(), timeStampOffset.longValue(), 0);
                }

                // Decodifica la correspondiente imágen de video
                List<BufferedImage> images = decodeVideoImage2(container, packet, videoCoder, resampler,
                        Double.valueOf(lowerLimit), videoStreamId, oper);
                // Reduce la imágen y la adiciona a la lista de snapshots
                if (images != null && !images.isEmpty()) {
                    for (BufferedImage bufferedImage : images) {
                        snapshots.add(dumpImage(bufferedImage));
                    }
                }

                // Crea las sprite images
                List<BufferedImage> lstSprites = createSpriteImages(snapshots);

                if (lstSprites != null && !lstSprites.isEmpty()) {
                    ret = lstSprites.get(0);
                }
            }
        } catch (Exception e) {
            log.debug("Error en ejecución xuggler: " + e);
        } finally {
            // Technically since we're exiting anyway, these will be cleaned up by the garbage collector
            if (videoCoder != null) {
                videoCoder.close();
            }
            if (container != null) {
                container.close();
                container = null;
            }
        }
        return ret;
    }

    /**
     * Reduce el tamaño de la imágen
     *
     * @author carlos polo
     * @version 1.0.0
     * @param originalImage
     * @since 6.0
     * @return BufferedImage
     * @date 25/03/2013
     */
    protected BufferedImage dumpImage(BufferedImage originalImage) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
        g.dispose();

        return resizedImage;
    }

    /**
     * Crea sprite images en base a los snapshots generados del video
     *
     * @author carlos polo
     * @version 1.0.0
     * @param snapshots
     * @since 6.0
     * @return List<BufferedImage>
     * @date 25/03/2013
     */
    protected List<BufferedImage> createSpriteImages(List<BufferedImage> snapshots) {
        int x = 0;
        int y = 0;
        int countCols = 0;
        int countRows = 0;
        List<BufferedImage> bufferedSprites = new ArrayList<BufferedImage>();

        try {
            if (!snapshots.isEmpty()) {
                BufferedImage sprite = new BufferedImage(imgWidth * 5, imgHeight * 5, BufferedImage.TYPE_INT_RGB);

                for (BufferedImage buffImage : snapshots) {
                    int width = buffImage.getWidth();
                    int height = buffImage.getHeight();

                    if (countCols == 5) {
                        x = 0;
                        countCols = 0;
                        y = y + height;
                        countRows++;
                    }
                    if (countRows == 5) {
                        x = 0;
                        y = 0;
                        countCols = 0;
                        countRows = 0;
                        bufferedSprites.add(sprite);
                        sprite = new BufferedImage(imgWidth * 5, imgHeight * 5, BufferedImage.TYPE_INT_RGB);
                    }

                    sprite.createGraphics().drawImage(buffImage, x, y, null); // 0, 0 are the x and y positions
                    x = x + width;
                    countCols++;
                }
                bufferedSprites.add(sprite);
            } else {
                log.debug("No hay bufferedImages (snapshots)");
            }
        } catch (Exception e) {
            log.debug("Error en ejecución xuggler: " + e);
        }
        return bufferedSprites;
    }

    /**
     * Decodifica la correspondiente imágen de video
     *
     * @author carlos polo
     * @version 1.0.0
     * @param container
     * @param packet
     * @param videoCoder
     * @param resampler
     * @param seconds
     * @param fileName
     * @param videoStreamId
     * @param oper
     * @since 6.0
     * @return BufferedImage
     * @date 25/03/2013
     */
    private List<BufferedImage> decodeVideoImage2(IContainer container, IPacket packet, IStreamCoder videoCoder,
            IVideoResampler resampler, Double seconds, int videoStreamId, long oper) {

        int count = 0;
        List<BufferedImage> snapshots = new ArrayList<BufferedImage>();
        BufferedImage biXuggler = new BufferedImage(videoCoder.getWidth(), videoCoder.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        IConverter converter = ConverterFactory.createConverter(biXuggler, IPixelFormat.Type.BGR24);
        try {
            while (container.readNextPacket(packet) >= 0) {
                // Now we have a packet, let's see if it belongs to our video stream
                if (packet.getStreamIndex() == videoStreamId) {
                    long pckDts = packet.getDts();
                    double num = Double.valueOf(pckDts) / Double.valueOf(oper);

                    // We allocate a new picture to get the data out of Xuggler
                    int offset = 0;
                    IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(), videoCoder.getWidth(),
                            videoCoder.getHeight());
                    videoCoder.decodeVideo(picture, packet, offset);

                    if (num >= Double.valueOf(seconds)) {
                        // We allocate a new picture to get the data out of Xuggle
                        while (offset < packet.getSize()) {
                            // Now, we decode the video, checking for any errors.
                            int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
                            if (bytesDecoded < 0) {
                                log.warn("WARNING!!! got no data decoding video in one packet");
                            }
                            offset += bytesDecoded;

                            // Some decoders will consume data in a packet, but will not be able to
                            // construct a full video picture yet. Therefore you should always check if
                            // you got a complete picture from the decode.
                            if (picture.isComplete()) {
                                IVideoPicture newPic = picture;

                                // If the resampler is not null, it means we didn't get the video in
                                // BGR24 format and need to convert it into BGR24 format.
                                if (resampler != null) {
                                    // we must resample
                                    newPic = IVideoPicture.make(resampler.getOutputPixelFormat(), picture.getWidth(),
                                            picture.getHeight());
                                }

                                // convert the BGR24 to an Java buffered image
                                snapshots.add(dumpImage(converter.toImage(newPic)));
                                count++;
                                seconds++;

                                if (count == 25) {
                                    break;
                                }
                            }
                        }
                        if (count == 25) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Error en ejecución xuggler: " + e);
        }
        return snapshots;
    }

    /**
     * Obtiene los dts del 1er y 2do keyframes del video Mayores o iguales a 1
     *
     * @author carlos polo
     * @version 1.0.0
     * @param container
     * @param packet
     * @param videoStreamId
     * @param oper
     * @since 6.0
     * @return Long[]
     * @date 25/03/2013
     */
    private Long[] getKeyFramesDts(IContainer container, IPacket packet, int videoStreamId, long oper) {
        int count = 0;
        long firstKeyDts = 0; // dts del 1er keyframe
        long secondKeyDts = 0; // dts del 2do keyframe

        while (container.readNextPacket(packet) >= 0) {
            if (packet.isComplete()) {
                // Valida que el paquete tenga el mismo index del stream Si el index del
                // paquete es diferente, lee el próximo paquete
                if (packet.getStreamIndex() != videoStreamId) {
                    continue;
                }

                // Is this object a key object? i.e. it can be interpreted without needing any other media objects
                if (!packet.isKey()) {
                    // lee el próximo paquete
                    continue;
                }

                long pckDts = packet.getDts();

                if (pckDts >= 0) {
                    if (count == 0) {
                        // Obtiene dts del 1er keyframe
                        firstKeyDts = pckDts;

                        if ((firstKeyDts / oper) < 1) {
                            // Si es menor a 1, busca otro keyframe para ser tenido en cuenta como el primero
                            continue;
                        }
                        count++;
                    } else {
                        // Obtiene dts del 2do keyframe
                        secondKeyDts = pckDts;

                        if ((secondKeyDts / oper) < 1) {
                            // Si es menor a 1, busca otro keyframe para ser tenido en cuenta como el primero
                            continue;
                        }
                        break;
                    }
                }
            }
        }
        return new Long[] { firstKeyDts, secondKeyDts };
    }

    /**
     * Obtiene duración del video en segundos
     *
     * @author carlos polo
     * @version 1.0.0
     * @param fileName ruta completa del archivo para saber su duracion
     * @since 6.0
     * @return Long
     * @date 25/03/2013
     */
    public Long getVideoDuration(String fileName) {
        // create a Xuggler container object
        IContainer container = IContainer.make();

        // open up the container
        if (container.open(fileName, IContainer.Type.READ, null) < 0) {
            log.error("could not open file: " + fileName);
        }

        return (container.getDuration() / 1000000);
    }
}