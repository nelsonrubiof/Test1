package com.scopix.test
{
    public class CalendarDataTest
    {
        private static var _instance:CalendarDataTest;
        
        public static function getInstance():CalendarDataTest {
            if(_instance == null) {
                _instance = new CalendarDataTest();
            }
            
            return _instance;
        }
        
        public function CalendarDataTest() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
    }
}