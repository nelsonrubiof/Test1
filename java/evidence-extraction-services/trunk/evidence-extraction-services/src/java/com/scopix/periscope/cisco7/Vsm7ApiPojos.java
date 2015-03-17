/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  Vsm7ApiPojos.java
 * 
 *  Created on 08-10-2013, 03:18:02 PM
 * 
 */
package com.scopix.periscope.cisco7;

/**
 *
 * @author Gustavo Alvarez
 */
public class Vsm7ApiPojos {
// Request POJO's

    static class LoginRequest {

        @SuppressWarnings("unused")
        private String username;
        @SuppressWarnings("unused")
        private String password;
        @SuppressWarnings("unused")
        private String domain;

        public LoginRequest(String u, String p, String d) {
            this.username = u;
            this.password = p;
            this.domain = d;
        }
    }

    // Generic status
    public static class Status {

        public String errorType;
        public String errorMsg;
        public String errorReason;
    }

    // Generic paginated data
    public static class Page {

        public Device[] items;
        public Boolean nextPageExists;
        public Boolean previousPageExists;
        public int totalRows;
    }

    // Response POJO's
    public static class LoginResponse {

        public static class Data {

            public String uid;
        }
        public Data data;
        public Status status;
    }

    public static class UmsResponse {

        public static class Data {

            public static class DeviceAccess {

                public String hostname_ip;
            }
            public DeviceAccess deviceAccess;
        }
        public Data data;
        public Status status;
    }

    public static class PageResponse {

        public Page data;
        public Status status;
    }

    public static class SecurityTokenResponse {

        public String data;
        public Status status;
    }

    // Objects model
    public static class NetworkInterfaces {

        public String macAddress;
        public String ipAddress;
        public String subnetMask;
    }

    public static class NetworkConfig {

        public NetworkInterfaces[] networkInterfaces;
    }

    public static class DeviceAccess {

        public String hostname_ip;
        public String uid;
    }

    public static class Device {

        public String name;
        public String uid;
        public String objectType;
        public DeviceAccess deviceAccess;
        public BaseRef managedByRef;
        public BaseRef locationRef;
        public NetworkConfig networkConfig;
    }

    public static class BaseRef {

        public String refName;
        public String refUid;
        public String refObjectType;
    }

    public static class Alert {

        public String name;
        public String uid;
        public String objectType;
        public String severity;
        public String alertTime;
        public String alertType;
        public BaseRef deviceRef;
        public String lastEventGenTime;
        public String lastEventType;
        public String locationPath;
        public String locationUid;
        public String description;
    }

    public static class AlertNotification {

        public Alert alert;
    }
}
