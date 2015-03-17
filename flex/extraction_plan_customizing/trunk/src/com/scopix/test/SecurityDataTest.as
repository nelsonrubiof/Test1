package com.scopix.test {
	import com.scopix.periscope.webservices.security.valueObjects.CorporateDTO;
	
	import mx.collections.ArrayCollection;

    public class SecurityDataTest {
        private static var _instance:SecurityDataTest;

        public static function getInstance():SecurityDataTest {
            if (_instance == null) {
                _instance = new SecurityDataTest();
            }

            return _instance;
        }

        public function SecurityDataTest() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }

        public function getCorporatesDTOData():ArrayCollection {
            var corporateList:ArrayCollection = new ArrayCollection();

            var corporate:CorporateDTO = new CorporateDTO();
            corporate.corporateId = 0;
            corporate.name = "scopix";
            corporate.description = "Scopix";
            //corporate.areaTypes = getAreaTypesCabelas();
            //corporate.stores = getStoreListCabelas();

            corporateList.addItem(corporate);

            //cabelas
            corporate = new CorporateDTO();
            corporate.corporateId = 1;
            corporate.name = "cabelas";
            corporate.description = "Cabelas";
            //corporate.areaTypes = getAreaTypesCabelas();
            //corporate.stores = getStoreListCabelas();

            corporateList.addItem(corporate);

            //lowes
            corporate = new CorporateDTO();
            corporate.corporateId = 2;
            corporate.name = "lowes";
            corporate.description = "Lowes";
            //corporate.areaTypes = getAreaTypesLowes();
            //corporate.stores = getStoreListLowes();

            corporateList.addItem(corporate);

            return corporateList;
        }
    }
}
