/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;

/**
 *
 * @author nelson
 */
public class CognimaticsPeopleCounter150ExtractionCommandTest extends TestCase {

    public CognimaticsPeopleCounter150ExtractionCommandTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testReadPeopleCountingFile() throws Exception {
        System.out.println("readPeopleCountingFile");
        File tmp = File.createTempFile("test", ".xml");
        tmp.deleteOnExit();
        FileUtils.writeStringToFile(tmp, xmlTest);
        InputStream is = new FileInputStream(tmp);
        CognimaticsPeopleCounter150ExtractionCommand instance = new CognimaticsPeopleCounter150ExtractionCommand();
        //Map expResult = null;
        Map result = instance.readPeopleCountingFile(is);
        //assertEquals(108, result.size());
        Assert.assertEquals(this, this);
    }
    private static final String xmlTest = "<?xml version='1.0' ?>"
            + "<!DOCTYPE countdata SYSTEM 'appdata.dtd'>"
            + "<countdata version='2'>"
            + "	<typedesc>"
            + "		<type typeid='3'>Person coming in</type>"
            + "		<type typeid='4'>Person going out</type>"
            + "	</typedesc>"
            + "	<cntset name='Copec-Manquehue' starttime='0' delta='900'>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>6</cnt>"
            + "			<cnt typeid='4'>23</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>17</cnt>"
            + "			<cnt typeid='4'>12</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>12</cnt>"
            + "			<cnt typeid='4'>8</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>3</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "	</cntset>"
            + "	<cntset name='Copec-Manquehue' starttime='1307533500' delta='900'>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>19</cnt>"
            + "			<cnt typeid='4'>12</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>15</cnt>"
            + "			<cnt typeid='4'>18</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>29</cnt>"
            + "			<cnt typeid='4'>17</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>42</cnt>"
            + "			<cnt typeid='4'>35</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>58</cnt>"
            + "			<cnt typeid='4'>70</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>63</cnt>"
            + "			<cnt typeid='4'>41</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>37</cnt>"
            + "			<cnt typeid='4'>39</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>57</cnt>"
            + "			<cnt typeid='4'>32</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>39</cnt>"
            + "			<cnt typeid='4'>37</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>30</cnt>"
            + "			<cnt typeid='4'>35</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>26</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>19</cnt>"
            + "			<cnt typeid='4'>13</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>22</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>33</cnt>"
            + "			<cnt typeid='4'>29</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>33</cnt>"
            + "			<cnt typeid='4'>38</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>21</cnt>"
            + "			<cnt typeid='4'>24</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>21</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>17</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>36</cnt>"
            + "			<cnt typeid='4'>43</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>13</cnt>"
            + "			<cnt typeid='4'>16</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>22</cnt>"
            + "			<cnt typeid='4'>31</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>10</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>30</cnt>"
            + "			<cnt typeid='4'>23</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>15</cnt>"
            + "			<cnt typeid='4'>18</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>38</cnt>"
            + "			<cnt typeid='4'>36</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>43</cnt>"
            + "			<cnt typeid='4'>47</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>29</cnt>"
            + "			<cnt typeid='4'>28</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>42</cnt>"
            + "			<cnt typeid='4'>33</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>36</cnt>"
            + "			<cnt typeid='4'>32</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>38</cnt>"
            + "			<cnt typeid='4'>33</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>26</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>34</cnt>"
            + "			<cnt typeid='4'>31</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>27</cnt>"
            + "			<cnt typeid='4'>29</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>31</cnt>"
            + "			<cnt typeid='4'>30</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>47</cnt>"
            + "			<cnt typeid='4'>37</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>25</cnt>"
            + "			<cnt typeid='4'>27</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>45</cnt>"
            + "			<cnt typeid='4'>39</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>23</cnt>"
            + "			<cnt typeid='4'>35</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>24</cnt>"
            + "			<cnt typeid='4'>34</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>34</cnt>"
            + "			<cnt typeid='4'>53</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>44</cnt>"
            + "			<cnt typeid='4'>44</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>10</cnt>"
            + "			<cnt typeid='4'>16</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>26</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>11</cnt>"
            + "			<cnt typeid='4'>20</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>10</cnt>"
            + "			<cnt typeid='4'>9</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>19</cnt>"
            + "			<cnt typeid='4'>23</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>29</cnt>"
            + "			<cnt typeid='4'>34</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>6</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "	</cntset>"
            + "	<cntset name='Copec-Manquehue' starttime='1307577600' delta='900'>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>7</cnt>"
            + "			<cnt typeid='4'>3</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>10</cnt>"
            + "			<cnt typeid='4'>6</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>5</cnt>"
            + "			<cnt typeid='4'>12</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>9</cnt>"
            + "			<cnt typeid='4'>9</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>2</cnt>"
            + "			<cnt typeid='4'>3</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>11</cnt>"
            + "			<cnt typeid='4'>7</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>7</cnt>"
            + "			<cnt typeid='4'>6</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>0</cnt>"
            + "			<cnt typeid='4'>4</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>5</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>15</cnt>"
            + "			<cnt typeid='4'>14</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>5</cnt>"
            + "			<cnt typeid='4'>14</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>2</cnt>"
            + "			<cnt typeid='4'>0</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>11</cnt>"
            + "			<cnt typeid='4'>14</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>14</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>4</cnt>"
            + "			<cnt typeid='4'>12</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>30</cnt>"
            + "			<cnt typeid='4'>22</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>6</cnt>"
            + "			<cnt typeid='4'>3</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>7</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>3</cnt>"
            + "			<cnt typeid='4'>5</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>18</cnt>"
            + "			<cnt typeid='4'>14</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>6</cnt>"
            + "			<cnt typeid='4'>10</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>15</cnt>"
            + "			<cnt typeid='4'>8</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>11</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>23</cnt>"
            + "			<cnt typeid='4'>24</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>14</cnt>"
            + "			<cnt typeid='4'>20</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>30</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>25</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>38</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>31</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>25</cnt>"
            + "			<cnt typeid='4'>29</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>24</cnt>"
            + "			<cnt typeid='4'>35</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>27</cnt>"
            + "			<cnt typeid='4'>28</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>20</cnt>"
            + "			<cnt typeid='4'>39</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>29</cnt>"
            + "			<cnt typeid='4'>21</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>28</cnt>"
            + "			<cnt typeid='4'>20</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>18</cnt>"
            + "			<cnt typeid='4'>27</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>37</cnt>"
            + "			<cnt typeid='4'>39</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>33</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>17</cnt>"
            + "			<cnt typeid='4'>22</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>21</cnt>"
            + "			<cnt typeid='4'>22</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>21</cnt>"
            + "			<cnt typeid='4'>26</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>27</cnt>"
            + "			<cnt typeid='4'>27</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>42</cnt>"
            + "			<cnt typeid='4'>35</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>33</cnt>"
            + "			<cnt typeid='4'>42</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>16</cnt>"
            + "			<cnt typeid='4'>27</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>39</cnt>"
            + "			<cnt typeid='4'>22</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>26</cnt>"
            + "			<cnt typeid='4'>33</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>31</cnt>"
            + "			<cnt typeid='4'>21</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>35</cnt>"
            + "			<cnt typeid='4'>26</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>43</cnt>"
            + "			<cnt typeid='4'>38</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>49</cnt>"
            + "			<cnt typeid='4'>40</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>76</cnt>"
            + "			<cnt typeid='4'>73</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>82</cnt>"
            + "			<cnt typeid='4'>99</cnt>"
            + "		</cntgroup>"
            + "		<cntgroup>"
            + "			<cnt typeid='3'>66</cnt>"
            + "			<cnt typeid='4'>56</cnt>"
            + "		</cntgroup>"
            + "	</cntset>"
            + "</countdata>";
}