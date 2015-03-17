<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="adminCorporateUrl" namespace="/corporatemanagement" action="corporate"/>
<s:url id="areaTypeUrl" namespace="/corporatemanagement" action="areaType"/>
<s:url id="areaUrl" namespace="/corporatemanagement" action="areas"/>
<s:url id="storeUrl" namespace="/corporatemanagement" action="stores"/>
<s:url id="essUrl" namespace="/corporatemanagement" action="evidenceServices"/>
<s:url id="eessUrl" namespace="/corporatemanagement" action="evidenceExtractionServices"/>
<s:url id="evidenceProviderUrl" namespace="/corporatemanagement" action="evidenceProvider"/>
<s:url id="countryUrl" namespace="/corporatemanagement" action="country"/>
<s:url id="regionUrl" namespace="/corporatemanagement" action="region"/>
<s:url id="sensorUrl" namespace="/corporatemanagement" action="sensor"/>
<s:url id="locationsUrl" namespace="/corporatemanagement" action="locationEP"/>
<h1><s:text name="label.corporateManagement"/></h1>
<br>
<s:tabbedPanel id="mainTabs" doLayout="false" cssStyle="width:100%" >
    <s:div id="serversTabDiv" key="tab.corporateManagement.servers" refreshOnShow="false" showLoadingText="false">
        <br>
        <s:tabbedPanel id="serversMainTabDiv" doLayout="false" cssStyle="width:100%">
            <s:div id="evidenceServicesTabDiv" key="tab.corporateManagement.evidence" refreshOnShow="false" href="%{essUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshServers" />
            <s:div id="evidenceExtractionTabDiv" key="tab.corporateManagement.evidenceExtraction" refreshOnShow="false" href="%{eessUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshServers" />
        </s:tabbedPanel>
    </s:div>
    <s:div id="corporateTabDiv" key="tab.corporateManagement.corporate" refreshOnShow="false" href="%{adminCorporateUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshCorporate"/>
    <s:div id="countryTabDiv" key="tab.corporateManagement.country" refreshOnShow="false" href="%{countryUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshCountry"/>
    <s:div id="regionTabDiv" key="tab.corporateManagement.region" refreshOnShow="false" href="%{regionUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshRegion"/>
    <s:div id="storesTabDiv" key="tab.corporateManagement.stores" refreshOnShow="false" href="%{storeUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshStores"/>
    <s:div id="areaTypeTabDiv" key="tab.corporateManagement.areaType" refreshOnShow="false" href="%{areaTypeUrl}" indicator="waitDiv" showLoadingText="false" />
    <s:div id="areasTabDiv" key="tab.corporateManagement.areas" refreshOnShow="false" href="%{areaUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshAreas"/>
    <s:div id="evidenceProviderTabDiv" key="tab.corporateManagement.evidenceProvider" refreshOnShow="false" href="%{evidenceProviderUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshEvidenceProvider"/>
    <s:div id="sensorTabDiv" key="tab.corporateManagement.sensor" refreshOnShow="false" href="%{sensorUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshSensor"/>
    <s:div id="locationsTabDiv" key="tab.corporateManagement.location" refreshOnShow="false" href="%{locationsUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshLocations"/>
</s:tabbedPanel>
