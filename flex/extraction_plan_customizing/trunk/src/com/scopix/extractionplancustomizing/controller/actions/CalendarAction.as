package com.scopix.extractionplancustomizing.controller.actions
{
    import com.scopix.extractionplancustomizing.controller.commands.CleanExtractionPlanRangeCommand;
    import com.scopix.extractionplancustomizing.controller.commands.CopyCalendarCommand;
    import com.scopix.extractionplancustomizing.controller.commands.CopyCalendarToEditionCommand;
    import com.scopix.extractionplancustomizing.controller.commands.CopyDayInDaysCommand;
    import com.scopix.extractionplancustomizing.controller.commands.DeleteExtractionPlanRangeCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetCopyCalendarMessagesCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeDetailListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanRangeListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetGeneralDataFromLastEPCSendedCommand;
    import com.scopix.extractionplancustomizing.controller.commands.RegenerateDetailForDaysCommand;
    import com.scopix.extractionplancustomizing.controller.commands.RegenerateDetailForRangeCommand;
    import com.scopix.extractionplancustomizing.controller.commands.SaveExtractionPlanRangeCommand;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingModel;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDayOfWeekVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeDetailVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfString;
    import com.scopix.extractionplancustomizing.model.events.CleanExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.CopyCalendarCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.CopyCalendarToEditionCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.CopyDayInDaysCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.DeleteExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetCopyCalendarMessagesCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.RegenerateDetailForDaysCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.SaveExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.DayOfWeekVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.model.vo.SensorVO;
    import com.scopix.extractionplancustomizing.view.CleanSelectDays;
    import com.scopix.extractionplancustomizing.view.CopyDaySelectDays;
    import com.scopix.extractionplancustomizing.view.Day;
    import com.scopix.extractionplancustomizing.view.Hour;
    import com.scopix.extractionplancustomizing.view.Range;
    import com.scopix.extractionplancustomizing.view.RangeDay;
    import com.scopix.extractionplancustomizing.view.RegenerateDetailsSelectDays;
    import com.scopix.extractionplancustomizing.view.SelectStoreSituationTemplate;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.util.DateUtil;
    import com.scopix.util.UtilityFunctions;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    import commons.util.StringUtil;
    
    import flash.display.DisplayObject;
    
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.core.FlexGlobals;
    import mx.events.CloseEvent;
    import mx.formatters.DateFormatter;
    import mx.managers.PopUpManager;
    import mx.resources.ResourceManager;
    
    public class CalendarAction
    {
        private static var _instance:CalendarAction;

        /** modelos usados **/
        private var securityModel:SecurityModel;
        private var epcModel:ExtractionPlanCustomizingModel;
        
        private var range:Range;
        private var selectStoreST:SelectStoreSituationTemplate;
        private var copyDaySelectDays:CopyDaySelectDays;
        private var regenerateDetailSelectDays:RegenerateDetailsSelectDays;
        private var cleanSelectDays:CleanSelectDays;
        private var df:DateFormatter;
        
        public function CalendarAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
            epcModel = ExtractionPlanCustomizingModel.getInstance();
            df = new DateFormatter();
            df.formatString = "JJ:NN";
        }

        public static function getInstance():CalendarAction {
            if (_instance == null) {
                _instance = new CalendarAction();
            }
            return _instance;
        }
        
        public function init():void {
            if (epcModel.loadFirstTime) {
                refreshCalendar();
                epcModel.loadFirstTime = false;
            }
        }
        
        public function loadEPRanges():void {
            var getEPRangesCommand:GetExtractionPlanRangeListCommand = new GetExtractionPlanRangeListCommand();
            getEPRangesCommand.addCommandListener(getEPRangesResult, applicationError);
            getEPRangesCommand.execute(epcModel.epc.id, securityModel.sessionId);
        }
        
        private function getEPRangesResult(evt:GetExtractionPlanRangeListCommandResultEvent):void {
            //cargar datos en el calendario
            //se deben mostrar las ventanitas en los distintos rangos de cada dia
            epcModel.calendarRangesList = evt.epRangeList;
            
            for each (var epr:ExtractionPlanRangeVO in epcModel.calendarRangesList) {
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
                        epcModel.sunday.addChild(rd);
                        break;
                    case 2:
                        epcModel.monday.addChild(rd);
                        break;
                    case 3:
                        epcModel.tuesday.addChild(rd);
                        break;
                    case 4:
                        epcModel.wednesday.addChild(rd);
                        break;
                    case 5:
                        epcModel.thursday.addChild(rd);
                        break;
                    case 6:
                        epcModel.friday.addChild(rd);
                        break;
                    case 7:
                        epcModel.saturday.addChild(rd);
                        break;
                }
            }
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function selectStoreSituationTemplate(obj:DisplayObject):void {
            selectStoreST = new SelectStoreSituationTemplate();
            PopUpManager.addPopUp(selectStoreST, obj, true);
			PopUpManager.bringToFront(selectStoreST);
			PopUpManager.centerPopUp(selectStoreST);
			selectStoreST.visible = true;
        }
        
        public function copyCalendar(stId:Number, storeId:Number):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModel.situationTemplateIdToCopy = stId;
            epcModel.storeIdToCopy = storeId;
            
            var command:GetCopyCalendarMessagesCommand = new GetCopyCalendarMessagesCommand();
            command.addCommandListener(getCopyCalendarMessagesResult, applicationError);
            command.execute(stId, storeId, securityModel.sessionId);
        }
        
        private function getCopyCalendarMessagesResult(evt:GetCopyCalendarMessagesCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            
            var messages:ArrayOfString = evt.messageList;
            
            //siempre debe venir al menos 1 mensaje
            if (messages != null && messages.length > 0) {
                
                var msgList:ArrayOfString = new ArrayOfString();
                for each (var msg:String in messages) {
                    msgList.addString(ResourceManager.getInstance().getString('resources',msg));
                }
                
                var message:String = StringUtil.join(msgList, "\n");
                
                Alert.show(ResourceManager.getInstance().getString
                    ('resources','selectStoreSituationTemplate.confirm_copy_calendar').concat("\n").concat(message), 
                           ResourceManager.getInstance().getString('resources','commons.warning'), 
                           Alert.YES | Alert.CANCEL, 
                           null, confirmCopyCalendar);
            }
        }
        
        private function confirmCopyCalendar(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpUtils.getInstance().showLoading(true);

                var command:CopyCalendarCommand = new CopyCalendarCommand();
                command.addCommandListener(copyCalendarResult, applicationError);
                command.execute(epcModel.epc.id, epcModel.situationTemplateIdToCopy,
                    epcModel.storeIdToCopy, securityModel.sessionId);
            }
        }
        
        private function copyCalendarResult(evt:CopyCalendarCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            
            PopUpManager.removePopUp(selectStoreST);
        }
        
        public function selectDaysToCopy(obj:DisplayObject):void {
            copyDaySelectDays = new CopyDaySelectDays();
            PopUpManager.addPopUp(copyDaySelectDays, obj, true);
			PopUpManager.bringToFront(copyDaySelectDays);
			PopUpManager.centerPopUp(copyDaySelectDays);
			copyDaySelectDays.visible = true;
        }
        
        public function copyDayToDays(sourceDay:DayOfWeekVO, days:ArrayOfDayOfWeekVO, detailAction:String):void {
            PopUpUtils.getInstance().showLoading(true);
            
            var command:CopyDayInDaysCommand = new CopyDayInDaysCommand();
            command.addCommandListener(copyDayInDaysResult, applicationError);
            command.execute(epcModel.epc.id, sourceDay.id, days, detailAction, securityModel.sessionId);
        }
        
        private function copyDayInDaysResult(evt:CopyDayInDaysCommandResultEvent):void {
            
            PopUpManager.removePopUp(copyDaySelectDays);
            refreshCalendar();
        }
        
        public function selectDaysToRegenerate(obj:DisplayObject):void {
            regenerateDetailSelectDays = new RegenerateDetailsSelectDays();
            PopUpManager.addPopUp(regenerateDetailSelectDays, obj, true);
			PopUpManager.bringToFront(regenerateDetailSelectDays);
			PopUpManager.centerPopUp(regenerateDetailSelectDays);
			regenerateDetailSelectDays.visible = true;
        }
        
        public function regenerateDetailForDays(days:ArrayOfDayOfWeekVO):void {
            PopUpUtils.getInstance().showLoading(true);
            
            var command:RegenerateDetailForDaysCommand = new RegenerateDetailForDaysCommand();
            command.addCommandListener(regenerateDetailForDaysResult, applicationError);
            command.execute(epcModel.epc.id, days, securityModel.sessionId);
        }
        
        private function regenerateDetailForDaysResult(evt:RegenerateDetailForDaysCommandResultEvent):void {
            
            PopUpManager.removePopUp(regenerateDetailSelectDays);
            refreshCalendar();
        }

        public function selectDaysToClean(obj:DisplayObject):void {
            cleanSelectDays = new CleanSelectDays();
            PopUpManager.addPopUp(cleanSelectDays, obj, true);
			PopUpManager.bringToFront(cleanSelectDays);
			PopUpManager.centerPopUp(cleanSelectDays);
			cleanSelectDays.visible = true;
        }
        
        public function cleanEPRDays(days:ArrayOfDayOfWeekVO):void {
            PopUpUtils.getInstance().showLoading(true);
            var command:CleanExtractionPlanRangeCommand = new CleanExtractionPlanRangeCommand();
            command.addCommandListener(cleanEPRCommandResult, applicationError);
            command.execute(epcModel.epc.id, days, securityModel.sessionId);
        }
        
        private function cleanEPRCommandResult(evt:CleanExtractionPlanRangeCommandResultEvent):void {
            
            PopUpManager.removePopUp(cleanSelectDays);
            refreshCalendar();
        }
        
        public function newRangeView(hour:Hour, day:Day, parentParam:DisplayObject):void {
            epcModel.hourOfDaySelected = hour;
            epcModel.dayOfWeekSelected = day;
            epcModel.rangeCreatedModified = false;
            
            range = new Range();
            epcModel.initialTimeRange = new Date("01/01/2010 " + hour.name + ":00");
            epcModel.endTimeRange = new Date("01/01/2010 " + hour.name + ":00");
            epcModel.samplesRange = 0;
            epcModel.frequencyRange = 30;
            epcModel.durationRange = 0;
            epcModel.eprTypeRange = "FIXED";
            epcModel.eprdList = new ArrayOfExtractionPlanRangeDetailVO();
            
            verifyEnableAutomatic();
            
            PopUpManager.addPopUp(range, parentParam, true);
            PopUpManager.bringToFront(range);
            PopUpManager.centerPopUp(range);
            range.visible = true;
        }
        
        public function editRangeView(rd:RangeDay):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModel.rangeCreatedModified = false;
            
            epcModel.dayOfWeekSelected = rd.parent as Day;
            epcModel.rangeIdToEdit = rd.rdId;
            
            //cargar detalle del rango
            var command:GetExtractionPlanRangeDetailListCommand = new GetExtractionPlanRangeDetailListCommand();
            command.addCommandListener(getEPRDResult, applicationError);
            command.execute(rd.rdId, securityModel.sessionId);
        }
        
        private function getEPRDResult(evt:GetExtractionPlanRangeDetailListCommandResultEvent):void {
            epcModel.eprdList = evt.eprd;
            
            //cargar rango
            var command:GetExtractionPlanRangeCommand = new GetExtractionPlanRangeCommand();
            command.addCommandListener(getEPRResult, applicationError);
            command.execute(epcModel.rangeIdToEdit, securityModel.sessionId);
        }
        
        private function getEPRResult(evt:GetExtractionPlanRangeCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            
            var epr:ExtractionPlanRangeVO = evt.epr;
            
            epcModel.initialTimeRange = epr.initialTime;
            epcModel.endTimeRange = epr.endTime;
            epcModel.samplesRange = epr.samples;
            epcModel.frequencyRange = epr.frecuency;
            epcModel.durationRange = epr.duration;
            epcModel.eprTypeRange = epr.eprType;

            range = new Range();
            range.rdId = epr.id;
            
            verifyEnableAutomatic();
            
            var parentParam:DisplayObject = FlexGlobals.topLevelApplication as DisplayObject;
            PopUpManager.addPopUp(range, parentParam, true);
            PopUpManager.bringToFront(range);
            PopUpManager.centerPopUp(range);
            range.visible = true;
        }
        
        public function regenerateDetail(epr:ExtractionPlanRangeVO):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModel.rangeCreatedModified = true;
            
            var command:RegenerateDetailForRangeCommand = new RegenerateDetailForRangeCommand();
            command.addCommandListener(regenerateDetailResult, applicationError);
            command.execute(epcModel.epc.id, epr, securityModel.sessionId);
        }
        
        private function regenerateDetailResult(evt:GetExtractionPlanRangeDetailListCommandResultEvent):void {
            epcModel.eprdList = evt.eprd;
            
            if (evt.eprd != null && evt.eprd.length > 0) {
                //se asume que el rango existe en este proceso
                range.rdId = evt.eprd.getExtractionPlanRangeDetailVOAt(0).extractionPlanRangeId;
            }
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function cancelRange(range:Range):void {
            PopUpManager.removePopUp(range);
            
            if (epcModel.rangeCreatedModified) {
                PopUpUtils.getInstance().showLoading(true);
                refreshCalendar();
            }
        }
        
        public function saveRange(range:ExtractionPlanRangeVO):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModel.rangeCreatedModified = true;
            
            if (validateOverlapped(range)) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('range.overlapped', 'commons.error');
                return;
            }
            //intervalo en segundos
            var interval:Number = DateUtil.getInterval(range.initialTime, range.endTime);
            
            //rango debe ser multiplo de la frecuencia
            trace("interval: " + interval + ", frequency:" + range.frecuency + ", mod: " + interval % (range.frecuency*60));
/*             if ((interval % (range.frecuency * 60)) != 0) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('range.range_multiple_of_frequency', 'commons.error');
                return;
            } */
            
            //rango debe ser mayor que frecuencia
            if (interval < (range.frecuency * 60)) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('range.range_insufficient_for_frequency', 'commons.error');
                return;
            }
            
            //duracion por numero de muestras debe ser menor o igual que la frecuencia
            if ((range.duration * range.samples) > (range.frecuency * 60)) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('range.frequency_insufficient_for_samples_duration', 'commons.error');
                return;
            }
            
            epcModel.eprToSave = range;
            
            var command:SaveExtractionPlanRangeCommand = new SaveExtractionPlanRangeCommand();
            command.addCommandListener(saveEPRResult, applicationError);
            command.execute(epcModel.epc.id, range, securityModel.sessionId);
        }
        
        private function saveEPRResult(evt:SaveExtractionPlanRangeCommandResultEvent):void {
            var rd:RangeDay = new RangeDay();
            var dayObj:Day = epcModel.dayOfWeekSelected as Day;
            var eprSaved:ExtractionPlanRangeVO = evt.epr;
            
            rd.rdId = eprSaved.id;
            rd.initialTime = eprSaved.initialTime;
            rd.endTime = eprSaved.endTime;
            rd.x = 2;
            rd.y = (((epcModel.eprToSave.initialTime.getHours() * 60) + epcModel.eprToSave.initialTime.getMinutes()) / 2);
            rd.height = (((epcModel.eprToSave.endTime.getHours() * 60) + epcModel.eprToSave.endTime.getMinutes()) - ((epcModel.eprToSave.initialTime.getHours() * 60) + epcModel.eprToSave.initialTime.getMinutes())) / 2; 
            
            dayObj.addChild(rd);
            
            rd.rangeTimeStr = df.format(eprSaved.initialTime) + " - " + df.format(eprSaved.endTime);
            rd.sampleStr = ResourceManager.getInstance().getString('resources','rangeday.samples') + " " + epcModel.eprToSave.samples.toString();
            rd.frequencyStr = ResourceManager.getInstance().getString('resources','rangeday.frequency') + " " + epcModel.eprToSave.frecuency.toString();
            rd.eprTypeStr = UtilityFunctions.getEPTypeDescription(epcModel.eprToSave.eprType);
            rd.durationStr = ResourceManager.getInstance().getString('resources','rangeday.duration') + " " + epcModel.eprToSave.duration.toString();

            
            PopUpManager.removePopUp(range);
            
            refreshCalendar();
        }
        
        public function deleteRangeDay(rd:RangeDay):void {
            //var day:Day = rd.parent as Day;
            epcModel.rangeToDelete = rd;
            
            Alert.show(ResourceManager.getInstance().getString
                ('resources','rangeday.delete_range'), 
                       ResourceManager.getInstance().getString('resources','commons.warning'), 
                       Alert.YES | Alert.CANCEL, 
                       null, confirmDeleteRange);
        }
        
        private function confirmDeleteRange(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpUtils.getInstance().showLoading(true);
                var command:DeleteExtractionPlanRangeCommand = new DeleteExtractionPlanRangeCommand();
                command.addCommandListener(deleteEPCRangeResult, applicationError);
                command.execute(epcModel.epc.id, epcModel.rangeToDelete.rdId, securityModel.sessionId);
            }
        }
        
        private function deleteEPCRangeResult(evt:DeleteExtractionPlanRangeCommandResultEvent):void {
            epcModel.rangeToDelete.parent.removeChild(epcModel.rangeToDelete);
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function backToEPCList():void {
            ExtractionPlanCustomizingAction.getInstance().backToEPCList();
        }

        private function refreshCalendar():void {
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.sunday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.monday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.tuesday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.wednesday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.thursday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.friday.removeRanges();
            FlexGlobals.topLevelApplication.newEditEPC.generalCalendar.calendar.calendarDetail.saturday.removeRanges();
            
            loadEPRanges();
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
                
                var command:GetGeneralDataFromLastEPCSendedCommand = new GetGeneralDataFromLastEPCSendedCommand();
                command.addCommandListener(getGeneralDataLastEPCSendedResult, applicationError);
                command.execute(epcModel.epc.situationTemplate.id, epcModel.storeIdSelected, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
            }
        }
        
        private function getGeneralDataLastEPCSendedResult(evt:GetExtractionPlanCustomizingGeneralDataCommandResultEvent):void {
            //si llega aqui, es porque el EPC con estado SENT existe (caso contrario hubiese lanzado una exception),
            //por lo tanto obtenemos los datos de dicho EPC y llamamos a la copia del calendario.
            var lastSentEPC:ExtractionPlanCustomizingVO = evt.epc;
            
            var command:CopyCalendarToEditionCommand = new CopyCalendarToEditionCommand();
            command.addCommandListener(copyCalendarToEditionResult, applicationError);
            command.execute(lastSentEPC.id, securityModel.sessionId, ExtractionPlanCustomizingLastSentModel.LAST_SENT_MODEL);
        }
        
        private function copyCalendarToEditionResult(evt:CopyCalendarToEditionCommandResultEvent):void {
            //PopUpUtils.getInstance().showLoading(false);
            
            refreshCalendar();
            //var epcCopied:ExtractionPlanCustomizingVO = evt.epc;
            
            //ExtractionPlanCustomizingAction.getInstance().manageEPC(epcCopied);
        }

        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
        
        /**
        * Esta función valida si un rango esta traslapado con otro. En caso de estarlo, devuelve true, de lo contrario false.
        **/
        private function validateOverlapped(range:ExtractionPlanRangeVO):Boolean {
            var resp:Boolean = false;
            
            var tempList:ArrayOfExtractionPlanRangeVO = getDayRangeList(range.dayOfWeek);
            
            for each (var epr:ExtractionPlanRangeVO in tempList) {
                if (epr.id != range.id &&
                	((range.initialTime.getTime() > epr.initialTime.getTime() && range.initialTime.getTime() < epr.endTime.getTime()) 
                      ||
                    (range.initialTime.getTime() < epr.initialTime.getTime() && range.endTime.getTime() > epr.endTime.getTime())
                      ||
                    (range.initialTime.getTime() < epr.initialTime.getTime() && range.endTime.getTime() > epr.initialTime.getTime()))) {

                    resp = true;
                    break;
                 }
            }
            
            return resp;
        }
        
        private function getDayRangeList(day:Number):ArrayOfExtractionPlanRangeVO {
            var list:ArrayOfExtractionPlanRangeVO = new ArrayOfExtractionPlanRangeVO();
            var childList:Array = null;
            
            switch (day) {
                case 1:
                    childList = epcModel.sunday.getChildren();
                    break;
                case 2:
                    childList = epcModel.monday.getChildren();
                    break;
                case 3:
                    childList = epcModel.tuesday.getChildren();
                    break;
                case 4:
                    childList = epcModel.wednesday.getChildren();
                    break;
                case 5:
                    childList = epcModel.thursday.getChildren();
                    break;
                case 6:
                    childList = epcModel.friday.getChildren();
                    break;
                case 7:
                    childList = epcModel.saturday.getChildren();
                    break;
            }
            
            var epr:ExtractionPlanRangeVO = null;
            for each (var obj:Object in childList) {
                if (obj is RangeDay) {
                    var rd:RangeDay = (obj as RangeDay);
                    epr = new ExtractionPlanRangeVO();
                    epr.id = rd.rdId;
                    epr.initialTime = rd.initialTime;
                    epr.endTime = rd.endTime;
                    
                    list.addExtractionPlanRangeVO(epr);
                }
            }
            
            return list;
        }
        
		public function verifyEnableAutomatic():void {
		    var cont:Number = 0;
		    
		    for each (var sensor:SensorVO in epcModel.sensorList) {
		        if (sensor.selected) {
		            cont++;
		            break;
		        }
		    }
		    
		    if (cont > 0) {
		        epcModel.enableAutomaticSeleccion = true;
		    } else {
		        epcModel.enableAutomaticSeleccion = false;
		    }
		}
    }
}