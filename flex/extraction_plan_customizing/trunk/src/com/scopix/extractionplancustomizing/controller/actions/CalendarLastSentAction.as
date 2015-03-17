package com.scopix.extractionplancustomizing.controller.actions
{
    import com.scopix.extractionplancustomizing.controller.commands.CopyCalendarToEditionCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeDetailListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeListCommand;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingModel;
    import com.scopix.extractionplancustomizing.model.events.CopyCalendarToEditionCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.view.lastSent.Day;
    import com.scopix.extractionplancustomizing.view.lastSent.Range;
    import com.scopix.extractionplancustomizing.view.lastSent.RangeDay;
    import com.scopix.global.ApplicationEffects;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.util.UtilityFunctions;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    
    import flash.display.DisplayObject;
    
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.core.FlexGlobals;
    import mx.events.CloseEvent;
    import mx.formatters.DateFormatter;
    import mx.managers.PopUpManager;
    import mx.resources.ResourceManager;
    
    public class CalendarLastSentAction
    {
        private static var _instance:CalendarLastSentAction;

        /** modelos usados **/
        private var securityModel:SecurityModel;
        private var epcModelLastSent:ExtractionPlanCustomizingLastSentModel;
        
        private var range:Range;
        private var df:DateFormatter;
        
        public function CalendarLastSentAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
            epcModelLastSent = ExtractionPlanCustomizingLastSentModel.getInstance();
            df = new DateFormatter();
            df.formatString = "JJ:NN";
        }

        public static function getInstance():CalendarLastSentAction {
            if (_instance == null) {
                _instance = new CalendarLastSentAction();
            }
            return _instance;
        }
        
        public function init():void {
            if (epcModelLastSent.loadFirstTime) {
                refreshCalendar();
                epcModelLastSent.loadFirstTime = false;
            }
        }
        
        public function loadEPRanges():void {
            var getEPRangesCommand:GetExtractionPlanRangeListCommand = new GetExtractionPlanRangeListCommand();
            getEPRangesCommand.addCommandListener(getEPRangesResult, applicationError);
            getEPRangesCommand.execute(epcModelLastSent.epc.id, securityModel.sessionId);
        }
        
        private function getEPRangesResult(evt:GetExtractionPlanRangeListCommandResultEvent):void {
            //cargar datos en el calendario
            //se deben mostrar las ventanitas en los distintos rangos de cada dia
            epcModelLastSent.calendarRangesList = evt.epRangeList;
            
            for each (var epr:ExtractionPlanRangeVO in epcModelLastSent.calendarRangesList) {
                var rd:RangeDay = new RangeDay();
                rd.rdId = epr.id;
                rd.initialTime = epr.initialTime;
                rd.endTime = epr.endTime;
                rd.x = 2;
                rd.y = (((epr.initialTime.getHours() * 60) + epr.initialTime.getMinutes()) / 2);
                rd.height = (((epr.endTime.getHours() * 60) + epr.endTime.getMinutes()) - ((epr.initialTime.getHours() * 60) + epr.initialTime.getMinutes())) / 2;
                rd.rangeTimeStr = df.format(epr.initialTime) + " - " + df.format(epr.endTime); 
                rd.sampleStr = ResourceManager.getInstance().getString('resources','rangeday.samples') + " " + epr.samples.toString();
                rd.frequencyStr = ResourceManager.getInstance().getString('resources','rangeday.frequency') + " " + epr.frecuency.toString();
                rd.eprTypeStr = UtilityFunctions.getEPTypeDescription(epr.eprType);
                rd.durationStr = ResourceManager.getInstance().getString('resources','rangeday.duration') + " " + epr.duration.toString();
                switch (epr.dayOfWeek) {
                    case 1:
                        epcModelLastSent.sunday.addChild(rd);
                        break;
                    case 2:
                        epcModelLastSent.monday.addChild(rd);
                        break;
                    case 3:
                        epcModelLastSent.tuesday.addChild(rd);
                        break;
                    case 4:
                        epcModelLastSent.wednesday.addChild(rd);
                        break;
                    case 5:
                        epcModelLastSent.thursday.addChild(rd);
                        break;
                    case 6:
                        epcModelLastSent.friday.addChild(rd);
                        break;
                    case 7:
                        epcModelLastSent.saturday.addChild(rd);
                        break;
                }
            }
            PopUpUtils.getInstance().showLoading(false);
        }

        public function editRangeView(rd:RangeDay):void {
            PopUpUtils.getInstance().showLoading(true);
            
            epcModelLastSent.dayOfWeekSelected = rd.parent as Day;
            epcModelLastSent.rangeIdToEdit = rd.rdId;
            
            //cargar detalle del rango
            var command:GetExtractionPlanRangeDetailListCommand = new GetExtractionPlanRangeDetailListCommand();
            command.addCommandListener(getEPRDResult, applicationError);
            command.execute(rd.rdId, securityModel.sessionId);
        }
        
        private function getEPRDResult(evt:GetExtractionPlanRangeDetailListCommandResultEvent):void {
            epcModelLastSent.eprdList = evt.eprd;
            
            //cargar rango
            var command:GetExtractionPlanRangeCommand = new GetExtractionPlanRangeCommand();
            command.addCommandListener(getEPRResult, applicationError);
            command.execute(epcModelLastSent.rangeIdToEdit, securityModel.sessionId);
        }
        
        private function getEPRResult(evt:GetExtractionPlanRangeCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            
            var epr:ExtractionPlanRangeVO = evt.epr;
            
            epcModelLastSent.initialTimeRange = epr.initialTime;
            epcModelLastSent.endTimeRange = epr.endTime;
            epcModelLastSent.samplesRange = epr.samples;
            epcModelLastSent.frequencyRange = epr.frecuency;
            epcModelLastSent.durationRange = epr.duration;
            epcModelLastSent.eprTypeRange = epr.eprType;

            range = new Range();
            range.rdId = epr.id;
            
            var parentParam:DisplayObject = FlexGlobals.topLevelApplication as DisplayObject;
            PopUpManager.addPopUp(range, parentParam, true);
            PopUpManager.bringToFront(range);
            PopUpManager.centerPopUp(range);
            range.visible = true;
        }
        
        public function cancelRange(range:Range):void {
            PopUpManager.removePopUp(range);
        }
        
        public function copyCalendarToEdition():void {
            Alert.show(ResourceManager.getInstance().getString
                ('resources','calendar.confirm_copy_to_edition'), 
                       ResourceManager.getInstance().getString('resources','commons.warning'), 
                       Alert.YES | Alert.CANCEL, 
                       null, confirmCopyCalendarToEdition);
        }
        
        private function confirmCopyCalendarToEdition(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpUtils.getInstance().showLoading(true);
                var command:CopyCalendarToEditionCommand = new CopyCalendarToEditionCommand();
                command.addCommandListener(copyCalendarToEditionResult, applicationError);
                command.execute(epcModelLastSent.epc.id, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
            }
        }
        
        private function copyCalendarToEditionResult(evt:CopyCalendarToEditionCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            epcModelLastSent.copyCalendarToEditionEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
            //var epcCopied:ExtractionPlanCustomizingVO = evt.epc;
            
            //ExtractionPlanCustomizingAction.getInstance().manageEPC(epcCopied);
        }

        public function backToEPCList():void {
            ExtractionPlanCustomizingAction.getInstance().backToEPCList();
        }
        
        private function refreshCalendar():void {
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.sunday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.monday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.tuesday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.wednesday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.thursday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.friday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendarLastSent.calendar.calendarDetail.saturday.removeRanges();
            
            loadEPRanges();            
        }
        
        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
		
		public function copyFullEPC():void {
			ExtractionPlanCustomizingLastSentAction.getInstance().copyFullEPC();
		}
    }
}