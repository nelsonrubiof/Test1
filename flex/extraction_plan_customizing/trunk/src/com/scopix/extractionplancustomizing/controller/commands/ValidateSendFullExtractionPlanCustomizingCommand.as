package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    import com.scopix.extractionplancustomizing.model.events.ValidateSendFullExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.global.GlobalParameters;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;

    public class ValidateSendFullExtractionPlanCustomizingCommand extends BaseCommand
    {
        public function ValidateSendFullExtractionPlanCustomizingCommand() {
            super(new ValidateSendFullExtractionPlanCustomizingCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(epcList:ArrayOfEPCVO):void {
            //validar lista
            var listReturn:ArrayOfEPCVO = validate(epcList);
            
            //devolver los EPC que seran reenviados en el listado
            (event as ValidateSendFullExtractionPlanCustomizingCommandResultEvent).list = listReturn;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }

        private function validate(list:ArrayOfEPCVO):ArrayOfEPCVO {
            var result:ArrayOfEPCVO = new ArrayOfEPCVO();
            
            for each (var epc:ExtractionPlanCustomizingVO in list) {
                if (epc.status.equals(EPCStatesEnum.EDITION) && epc.canBeSent) {
                    result.addEPCVO(epc);
                }
            }
            
            return result;
        }
    }
}