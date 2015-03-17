/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityManager.java
 *
 * Created on 29-04-2008, 09:14:59 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.securitymanagement.commands.GetCorporatesForUserCommand;
import com.scopix.periscope.periscopefoundation.mailer.SimpleMailer;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.commands.AddRoleCommand;
import com.scopix.periscope.securitymanagement.commands.AddRolesGroupCommand;
import com.scopix.periscope.securitymanagement.commands.AddUserCommand;
import com.scopix.periscope.securitymanagement.commands.DeleteRolesGroupCommand;
import com.scopix.periscope.securitymanagement.commands.DeleteUserCommand;
import com.scopix.periscope.securitymanagement.commands.ExportUsersToJoomlaCommand;
import com.scopix.periscope.securitymanagement.commands.FindSpecificRolesGroupByNameCommand;
import com.scopix.periscope.securitymanagement.commands.FindSpecificUserByNameCommand;
import com.scopix.periscope.securitymanagement.commands.GetAreaTypeListCommand;
import com.scopix.periscope.securitymanagement.commands.GetAreaTypesForCorporateCommand;
import com.scopix.periscope.securitymanagement.commands.GetAreaTypesForUserCommand;
import com.scopix.periscope.securitymanagement.commands.GetCorporateListCommand;
import com.scopix.periscope.securitymanagement.commands.GetCorporatesForUserMinimalInfoCommand;
import com.scopix.periscope.securitymanagement.commands.GetRoleCommand;
import com.scopix.periscope.securitymanagement.commands.GetRoleListCommand;
import com.scopix.periscope.securitymanagement.commands.GetRolesGroupCommand;
import com.scopix.periscope.securitymanagement.commands.GetRolesGroupListCommand;
import com.scopix.periscope.securitymanagement.commands.GetRolesGroupsForUserCommand;
import com.scopix.periscope.securitymanagement.commands.GetStoreListCommand;
import com.scopix.periscope.securitymanagement.commands.GetStoresForCorporateCommand;
import com.scopix.periscope.securitymanagement.commands.GetStoresForUserCommand;
import com.scopix.periscope.securitymanagement.commands.GetUserCommand;
import com.scopix.periscope.securitymanagement.commands.GetUserListCommand;
import com.scopix.periscope.securitymanagement.commands.GetUserListMinimalInfoCommand;
import com.scopix.periscope.securitymanagement.commands.GetUserPrivilegesCommand;
import com.scopix.periscope.securitymanagement.commands.LoginCommand;
import com.scopix.periscope.securitymanagement.commands.LogoutCommand;
import com.scopix.periscope.securitymanagement.commands.UpdateRolesGroupCommand;
import com.scopix.periscope.securitymanagement.commands.UpdateUserCommand;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserConverter;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.RolesGroupConverter;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = SecurityManager.class, initMethod = "init")
@Transactional(rollbackFor = {ScopixException.class})
public class SecurityManager {

    private static Logger log = Logger.getLogger(SecurityManager.class);
    protected static Map usersPrivileges = new HashMap<Integer, Map>();
    private AddUserCommand addUserCommand;
    private LoginCommand loginCommand;
    private ExportUsersToJoomlaCommand usersToJoomlaCommand;
    private GetRolesGroupsForUserCommand rolesGroupsForUserCommand;
    private UpdateUserCommand updateUserCommand;
    private GetCorporatesForUserMinimalInfoCommand corporatesForuserMinimalInfoCommand;
    private GetUserListMinimalInfoCommand userListMinimalInfoCommand;
    private GetStoresForUserCommand storesForUserCommand;
    private GetAreaTypesForUserCommand areaTypesForUserCommand;
    private GetStoresForCorporateCommand storesForCorporateCommand;
    private GetAreaTypesForCorporateCommand areaTypesForCorporateCommand;

    private void init() {
        CheckOldLoginThread t = new CheckOldLoginThread();
        t.setName("CheckOldLoginThread");
        t.start();
    }

    /**
     *
     * @param areaType
     * @param sessionId
     * @return
     */
    public List<AreaType> getAreaTypeList(AreaType areaType, long sessionId) throws ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, SecurityManagerPermissions.GET_LIST_AREATYPE_PERMISSION);

        GetAreaTypeListCommand command = new GetAreaTypeListCommand();
        List<AreaType> areaTypes = command.execute(areaType);

        log.debug("finish");
        return areaTypes;
    }

    public List<Corporate> getCorporateList(Corporate corporate, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        secMan.checkSecurity(sessionId, SecurityManagerPermissions.GET_LIST_CORPORATE_PERMISSION);

        GetCorporateListCommand command = new GetCorporateListCommand();
        List<Corporate> corporates = command.execute(corporate);

        log.debug("finish");
        return corporates;
    }

    /**
     *
     * @param store
     * @param sessionId
     * @return
     */
    public List<Store> getStoreList(Store store, long sessionId) throws ScopixException {
        log.debug("start");
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                checkSecurity(sessionId, SecurityManagerPermissions.GET_LIST_STORE_PERMISSION);

        GetStoreListCommand command = new GetStoreListCommand();
        List<Store> stores = command.execute(store);

        log.debug("finish");
        return stores;
    }

    public long login(String user, String password, Integer corporateId, String application) throws ScopixException {
        log.info("start, user = " + user + " password = " + password);

        Long sessionId = null;
        sessionId = getLoginCommand().execute(user, password, corporateId, application);

        Map<String, Object> sessionMap = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        sessionMap.put("lastAccess", cal);
        GetUserPrivilegesCommand getUserPrivilegesCommand = new GetUserPrivilegesCommand();
        List<String> privileges = getUserPrivilegesCommand.execute(user, password);
        sessionMap.put("privileges", privileges);
        sessionMap.put("userName", user);
        sessionMap.put("corporateId", corporateId);
        usersPrivileges.put(sessionId, sessionMap);
        log.info("end, sessionId = " + sessionId);

        return sessionId;
    }

    public long login(String user, String password, Integer corporateId) throws ScopixException {
        return login(user, password, corporateId, null);
    }

    public long login(String user, String password) throws ScopixException {
        return login(user, password, null);
//        LoginCommand command = new LoginCommand();
//        Long sessionId = command.execute(user, password, null);
//
//        HashMap sessionMap = new HashMap();
//        Calendar cal = Calendar.getInstance();
//        sessionMap.put("lastAccess", cal);
//        GetUserPrivilegesCommand getUserPrivilegesCommand = new GetUserPrivilegesCommand();
//        List<String> privileges = getUserPrivilegesCommand.execute(user, password);
//        sessionMap.put("privileges", privileges);
//        sessionMap.put("userName", user);
//        usersPrivileges.put(sessionId, sessionMap);
//        log.debug("end, sessionId = " + sessionId);
//        return sessionId;
    }

    public Boolean checkSecurity(Long sessionId, String privilegeId) throws ScopixException {
        log.debug("start, sessionId = " + sessionId + " privilegeId = " + privilegeId);
        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);
        String message = "NO MESSAGE";
        if (sessionMap == null) {
            message = SecurityExceptionType.SESSION_NOT_FOUND.name();
            log.error("Error = " + message);
            throw new ScopixException(SecurityExceptionType.SESSION_NOT_FOUND.name());
        }
        sessionMap.put("lastAccess", Calendar.getInstance());
        List privileges = (List) sessionMap.get("privileges");
        int privilege = Collections.binarySearch(privileges, privilegeId);
        log.debug("privilege = " + privilege);
        if (privilege < 0) {
            message = SecurityExceptionType.ACCESS_DENIED.name();
            log.error("Error = " + message);
            throw new ScopixException(SecurityExceptionType.ACCESS_DENIED.name());
        }
        log.debug("end");
        return true;
    }

    public void logout(Long sessionId) {
        log.debug("start, sessionId = " + sessionId);
        LogoutCommand command = new LogoutCommand();
        command.execute(sessionId);
        usersPrivileges.remove(sessionId);
        log.debug("end");
    }

    public String getUserName(long sessionId) throws ScopixException {
        log.debug("start");
        String userName = null;
        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);
        if (sessionMap != null) {
            userName = (String) sessionMap.get("userName");
        } else {
            log.error("Error = " + SecurityExceptionType.SESSION_NOT_FOUND.name());
            throw new ScopixException("NOT FOUND");
        }
        log.debug("end, userName = " + userName);
        return userName;
    }

    /**
     * Returns the corporate Id from the current user session
     *
     * @param sessionId
     * @return integer representing the corporateId
     * @throws ScopixException
     */
    public Integer getCorporateId(Long sessionId) throws ScopixException {
        log.debug("start");
        Integer corporateId = null;
        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);
        if (sessionMap != null) {
            corporateId = (Integer) sessionMap.get("corporateId");
        } else {
            log.error("Error = " + SecurityExceptionType.SESSION_NOT_FOUND.name());
            throw new ScopixException("NOT FOUND");
        }
        log.debug("end, corporateId= " + corporateId);
        return corporateId;
    }

    //Security Management Business    
    public List<PeriscopeUser> getUserList(PeriscopeUser periscopeUser, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_USER_LIST_PERMISSION);
        List<PeriscopeUser> periscopeUsers = null;
        GetUserListCommand command = new GetUserListCommand();
        periscopeUsers = command.execute(periscopeUser);
        log.debug("end, result = " + periscopeUsers);
        return periscopeUsers;
    }

    public List<PeriscopeUserDTO> getUserListDTOs(PeriscopeUser periscopeUser, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_USER_LIST_PERMISSION);
        List<PeriscopeUserDTO> periscopeUserDTOs = new ArrayList<PeriscopeUserDTO>();
        GetUserListCommand command = new GetUserListCommand();
        List<PeriscopeUser> periscopeUsers = command.execute(periscopeUser);

        PeriscopeUserConverter converter = new PeriscopeUserConverter();

        for (PeriscopeUser pu : periscopeUsers) {
            periscopeUserDTOs.add(converter.convertToDTO(pu));
        }
        log.debug("end, result = " + periscopeUsers);
        return periscopeUserDTOs;
    }

    public List<PeriscopeUserDTO> getUserListMinimalInfo(Integer CorporInteger, long sessionId)
            throws ScopixException {
        log.info("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_USER_LIST_PERMISSION);

        List<PeriscopeUserDTO> list = getUserListMinimalInfoCommand().execute(CorporInteger);

        log.info("end");
        return list;
    }

    public PeriscopeUser getUser(int id, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_USER_PERMISSION);
        PeriscopeUser periscopeUser = null;
        GetUserCommand command = new GetUserCommand();
        periscopeUser = command.execute(id);
        log.debug("end, result = " + periscopeUser);
        return periscopeUser;
    }

    /**
     * utiliado desde un Action
     */
    public void addUser(PeriscopeUser periscopeUser, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.ADD_USER_PERMISSION);
        getAddUserCommand().execute(periscopeUser);
        log.debug("end");
    }

    /**
     * utiliado desde el WebService
     */
    public PeriscopeUser addUser(PeriscopeUserDTO periscopeUserDTO, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.ADD_USER_PERMISSION);

        PeriscopeUser periscopeUser = convertFromDTO(periscopeUserDTO);
        getAddUserCommand().execute(periscopeUser);

        log.debug("end");
        return periscopeUser;
    }

    public AddUserCommand getAddUserCommand() {
        if (addUserCommand == null) {
            addUserCommand = new AddUserCommand();
        }
        return addUserCommand;
    }

    public void setAddUserCommand(AddUserCommand addUserCommand) {
        this.addUserCommand = addUserCommand;
    }

    public void updateUser(PeriscopeUser periscopeUser, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.UPDATE_USER_PERMISSION);
        UpdateUserCommand command = new UpdateUserCommand();
        command.execute(periscopeUser);
        log.debug("end");
    }

    public void updateUser(PeriscopeUserDTO periscopeUserDTO, long sessionId)
            throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.UPDATE_USER_PERMISSION);
        UpdateUserCommand command = new UpdateUserCommand();
        PeriscopeUser periscopeUser = convertFromDTO(periscopeUserDTO);
        command.execute(periscopeUser);
        log.debug("end");
    }

    public void updateUserList(List<PeriscopeUserDTO> periscopeUserDTOs, long sessionId)
            throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.UPDATE_USER_PERMISSION);

        PeriscopeUserConverter periscopeUserConverter = new PeriscopeUserConverter();
        for (PeriscopeUserDTO periscopeUserDTO : periscopeUserDTOs) {
            PeriscopeUser periscopeUser = periscopeUserConverter.convertFromDTO(periscopeUserDTO);
            getUpdateUserCommand().execute(periscopeUser);
        }

        log.debug("end");
    }

    public UpdateUserCommand getUpdateUserCommand() {
        if (updateUserCommand == null) {
            updateUserCommand = new UpdateUserCommand();
        }
        return updateUserCommand;
    }

    public void setUpdateUserCommand(UpdateUserCommand updateUserCommand) {
        this.updateUserCommand = updateUserCommand;
    }

    public PeriscopeUser findSpecificUserByName(String name, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.FIND_SPECIFIC_USER_BY_NAME_PERMISSION);
        FindSpecificUserByNameCommand command = new FindSpecificUserByNameCommand();
        PeriscopeUser periscopeUser = command.execute(name);
        log.debug("end, result = " + periscopeUser);
        return periscopeUser;
    }

    public void deleteUser(int id, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.DELETE_USER_PERMISSION);
        DeleteUserCommand command = new DeleteUserCommand();
        command.execute(id);
        log.debug("end");
    }

    public void deleteUserList(List<Integer> list, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.DELETE_USER_PERMISSION);
        DeleteUserCommand command = new DeleteUserCommand();
        for (int id : list) {
            command.execute(id);
        }
        log.debug("end");
    }

    /**
     * Obtiene Una lista de RolesGroup basado en un filtro
     *
     * @param rolesGroup filtro usado para obtener los RoleGroups
     * @param sessionId
     * @return
     * @throws ScopixException
     * @throws ScopixException
     */
    public List<RolesGroup> getRolesGroupList(RolesGroup rolesGroup, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLES_GROUP_LIST_PERMISSION);
        GetRolesGroupListCommand command = new GetRolesGroupListCommand();
        List<RolesGroup> rolesGroups = command.execute(rolesGroup);
        log.debug("end, result = " + rolesGroups);
        return rolesGroups;
    }

    public List<RolesGroupDTO> getRolesGroupDTOList(RolesGroup rolesGroup, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLES_GROUP_LIST_PERMISSION);
        GetRolesGroupListCommand command = new GetRolesGroupListCommand();
        List<RolesGroup> rolesGroups = command.execute(rolesGroup);
        List<RolesGroupDTO> rolesGroupDTOs = new ArrayList<RolesGroupDTO>();
        RolesGroupConverter converter = new RolesGroupConverter();
        for (RolesGroup rg : rolesGroups) {
            rolesGroupDTOs.add(converter.convertToDTO(rg));
        }
        log.debug("end, result = " + rolesGroups);
        return rolesGroupDTOs;
    }

    public RolesGroup getRolesGroup(int id, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLES_GROUP_PERMISSION);
        GetRolesGroupCommand command = new GetRolesGroupCommand();
        RolesGroup rolesGroup = command.execute(id);
        log.debug("end, result = " + rolesGroup);
        return rolesGroup;
    }

    public RolesGroup findSpecificRolesGroupByName(String name, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.FIND_SPECIFIC_ROLES_GROUP_BY_NAME_PERMISSION);
        FindSpecificRolesGroupByNameCommand command = new FindSpecificRolesGroupByNameCommand();
        RolesGroup rolesGroup = command.execute(name);
        log.debug("end, result = " + rolesGroup);
        return rolesGroup;
    }

    public void addRolesGroup(RolesGroup rolesGroup, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.ADD_ROLES_GROUP_PERMISSION);
        AddRolesGroupCommand command = new AddRolesGroupCommand();
        command.execute(rolesGroup);
        log.debug("end");
    }

    public void updateRolesGroup(RolesGroup rolesGroup, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.UPDATE_ROLES_GROUP_PERMISSION);
        UpdateRolesGroupCommand command = new UpdateRolesGroupCommand();
        command.execute(rolesGroup);
        log.debug("end");
    }

    public void deleteRolesGroup(int id, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.DELETE_ROLES_GROUP_PERMISSION);
        DeleteRolesGroupCommand command = new DeleteRolesGroupCommand();
        command.execute(id);
        log.debug("end");
    }

    public List<Role> getRolesList(Role role, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLES_LIST_PERMISSION);
        GetRoleListCommand command = new GetRoleListCommand();
        List<Role> roles = command.execute(role);
        log.debug("end, result = " + roles);
        return roles;
    }

    public Role getRole(int id, long sessionId) throws ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLE_PERMISSION);
        GetRoleCommand command = new GetRoleCommand();
        Role role = command.execute(id);
        log.debug("end, result = " + role);
        return role;
    }

    public void addRole(Role role) {
        log.debug("start");
        AddRoleCommand command = new AddRoleCommand();
        command.execute(role);
        log.debug("end");
    }

    public void changePass(String user, String oldPassword, String newPassword, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.CHANGE_PASSWORD_PERMISSION);
        FindSpecificUserByNameCommand command = new FindSpecificUserByNameCommand();
        PeriscopeUser periscopeUser = command.execute(user);
        if (periscopeUser != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
                //get bytes in ASCII
                byte[] plainText = oldPassword.getBytes("ASCII");
                messageDigest.update(plainText);
                //Encode new password
                String newPwd = this.hexEncode(messageDigest.digest());
                if (newPwd.equals(periscopeUser.getPassword())) {
                    plainText = newPassword.getBytes("ASCII");
                    messageDigest.update(plainText);
                    //Encode new password
                    newPwd = this.hexEncode(messageDigest.digest());
                    periscopeUser.setPassword(newPwd);
                    UpdateUserCommand updateUserCommand = new UpdateUserCommand();
                    updateUserCommand.execute(periscopeUser);
                } else {
                    throw new ScopixException("Incorrect Password");
                }
            } catch (UnsupportedEncodingException ex) {
                log.error("UnsupportedEncodingException = " + ex.getMessage(), ex);
                throw new ScopixException(ex);
            } catch (NoSuchAlgorithmException ex) {
                log.error("NoSuchAlgorithmException = " + ex.getMessage(), ex);
                throw new ScopixException(ex);
            }
        }
        log.debug("end");
    }

    public List<Integer> getStoreIdsForUser(Integer corporateId, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start, corporateId = " + corporateId + ", sessionId = " + sessionId);
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_STORES_FOR_USER_PERMISSION);
        List<Integer> storeIds = new ArrayList<Integer>();
        String name = this.getUserName(sessionId);
        FindSpecificUserByNameCommand command = new FindSpecificUserByNameCommand();
        PeriscopeUser user = command.execute(name);
        if (user != null && user.getStores() != null && !user.getStores().isEmpty()) {
            for (Store store : user.getStores()) {
                log.debug("store.getCorporate().getId() = " + store.getCorporate().getId());
                if (store.getCorporate().getId().intValue() == corporateId.intValue()) {
                    log.debug("store.getCorporateStoreId() = " + store.getCorporateStoreId());
                    storeIds.add(store.getCorporateStoreId());
                }
            }
        }
        log.debug("end");
        return storeIds;
    }

    public List<Integer> getAreaIdsForUser(Integer corporateId, long sessionId) throws ScopixException,
            ScopixException {
        log.debug("start");
        this.checkSecurity(sessionId, SecurityManagerPermissions.GET_AREA_TYPES_FOR_USER_PERMISSION);
        List<Integer> areaIds = new ArrayList<Integer>();
        String name = this.getUserName(sessionId);
        FindSpecificUserByNameCommand command = new FindSpecificUserByNameCommand();
        PeriscopeUser user = command.execute(name);
        if (user != null) {
            for (AreaType areaType : user.getAreaTypes()) {
                if (areaType.getCorporate().getId().equals(corporateId)) {
                    areaIds.add(areaType.getCorporateAreaTypeId());
                }
            }
        }
        log.debug("end");
        return areaIds;
    }

    public void passwordRecover(String userName) throws ScopixException {
        log.debug("start");
        FindSpecificUserByNameCommand command = new FindSpecificUserByNameCommand();
        PeriscopeUser periscopeUser = command.execute(userName);
        if (periscopeUser != null && periscopeUser.getName() != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
                //generate a random number
                String randomNum = new Integer(Math.abs(prng.nextInt())).toString();
                //get bytes in ASCII
                byte[] plainText = randomNum.getBytes("ASCII");
                messageDigest.update(plainText);
                //Encode new password
                String newPwd = this.hexEncode(messageDigest.digest());
                periscopeUser.setPassword(newPwd);
                periscopeUser.setModificationDate(new Date());
                String message = "The password for the user " + userName + " is: " + randomNum + "\n\n"
                        + "La clave para el usuario " + userName + " es: " + randomNum + "\n\n";
                SimpleMailer.getInstance().send(periscopeUser.getEmail(), "Periscope Password Recovery", message);

            } catch (UnsupportedEncodingException ex) {
                log.error("UnsupportedEncodingException = " + ex.getMessage(), ex);
                throw new ScopixException(ex);
            } catch (NoSuchAlgorithmException ex) {
                log.error("NoSuchAlgorithmException = " + ex.getMessage(), ex);
                throw new ScopixException(ex);
            }
        } else {
            throw new ScopixException(SecurityExceptionType.USER_NOT_FOUND.name());
        }
        log.debug("end");
    }

    private String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }

    public List<CorporateDTO> getCorporatesForUser(String userName, long sessionId)
            throws ScopixException {
        log.debug("start");
        List<CorporateDTO> corporateDTOs = null;

        GetCorporatesForUserCommand command = new GetCorporatesForUserCommand();
        corporateDTOs = command.execute(userName);

        log.debug("end");
        return corporateDTOs;
    }

    public List<CorporateDTO> getCorporatesForUserMinimalInfo(String userName, long sessionId)
            throws ScopixException {
        log.info("start");

        List<CorporateDTO> list = getCorporatesForuserMinimalInfoCommand().execute(userName);

        log.info("end");
        return list;
    }

    public List<RolesGroupDTO> getRolesGroupListForUser(String username, long sessionId) throws ScopixException {
        log.info("start");

        List<RolesGroupDTO> rolesGroupDTOs = getRolesGroupsForUserCommand().execute(username);

        log.info("end");
        return rolesGroupDTOs;
    }

    public void sendUserJoomla(PeriscopeUserDTO periscopeUserDTO) {
        try {
            PeriscopeUserConverter periscopeUserConverter = new PeriscopeUserConverter();
            PeriscopeUser periscopeUser = periscopeUserConverter.convertFromDTO(periscopeUserDTO);
            /**
             * exportamos el usuario a joomla
             */
            getUsersToJoomlaCommand().execute(periscopeUser.getMainCorporate().getName());
        } catch (ScopixException e) {
            log.error("NO es posible enviar los usuario a joomla " + e, e);
        }
    }

    public ExportUsersToJoomlaCommand getUsersToJoomlaCommand() {
        if (usersToJoomlaCommand == null) {
            usersToJoomlaCommand = new ExportUsersToJoomlaCommand();
        }
        return usersToJoomlaCommand;
    }

    public void setUsersToJoomlaCommand(ExportUsersToJoomlaCommand userToJoomlaCommand) {
        this.usersToJoomlaCommand = userToJoomlaCommand;
    }

    public Set<String> getRoles(long sessionId) throws ScopixException {
        try {
            checkSecurity(sessionId, SecurityManagerPermissions.GET_ROLES_LIST_PERMISSION);
        } catch (ScopixException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        Set<String> roles = null;
        HashMap sessionMap = (HashMap) usersPrivileges.get(sessionId);
        List<String> privileges = (List) sessionMap.get("privileges");
        roles = new HashSet<String>(privileges);
        return roles;
    }

    public LoginCommand getLoginCommand() {
        if (loginCommand == null) {
            loginCommand = new LoginCommand();
        }
        return loginCommand;
    }

    public void setLoginCommand(LoginCommand loginCommand) {
        this.loginCommand = loginCommand;
    }

    public PeriscopeUser convertFromDTO(PeriscopeUserDTO periscopeUserDTO) {
        log.info("start");
        PeriscopeUser periscopeUser = null;
        try {
            PeriscopeUserConverter periscopeUserConverter = new PeriscopeUserConverter();
            periscopeUser = periscopeUserConverter.convertFromDTO(periscopeUserDTO);
        } catch (ScopixException e) {
            log.error("No es posible convertir usuario " + e, e);
        }
        log.info("end");
        return periscopeUser;
    }

    public GetCorporatesForUserMinimalInfoCommand getCorporatesForuserMinimalInfoCommand() {
        if (corporatesForuserMinimalInfoCommand == null) {
            corporatesForuserMinimalInfoCommand = new GetCorporatesForUserMinimalInfoCommand();
        }
        return corporatesForuserMinimalInfoCommand;
    }

    public void setCorporatesForuserMinimalInfoCommand(GetCorporatesForUserMinimalInfoCommand corporatesForuserMinimalInfoCommand) {
        this.corporatesForuserMinimalInfoCommand = corporatesForuserMinimalInfoCommand;
    }

    public GetUserListMinimalInfoCommand getUserListMinimalInfoCommand() {
        if (userListMinimalInfoCommand == null) {
            userListMinimalInfoCommand = new GetUserListMinimalInfoCommand();
        }
        return userListMinimalInfoCommand;
    }

    public void setUserListMinimalInfoCommand(GetUserListMinimalInfoCommand userListMinimalInfoCommand) {
        this.userListMinimalInfoCommand = userListMinimalInfoCommand;
    }

    public GetRolesGroupsForUserCommand getRolesGroupsForUserCommand() {
        if (rolesGroupsForUserCommand == null) {
            rolesGroupsForUserCommand = new GetRolesGroupsForUserCommand();
        }
        return rolesGroupsForUserCommand;
    }

    public void setRolesGroupsForUserCommand(GetRolesGroupsForUserCommand rolesGroupsForUserCommand) {
        this.rolesGroupsForUserCommand = rolesGroupsForUserCommand;
    }

    public GetStoresForUserCommand getStoresForUserCommand() {
        if (storesForUserCommand == null) {
            storesForUserCommand = new GetStoresForUserCommand();
        }
        return storesForUserCommand;
    }

    public void setStoresForUserCommand(GetStoresForUserCommand storesForUserCommand) {
        this.storesForUserCommand = storesForUserCommand;
    }

    public GetAreaTypesForUserCommand getAreaTypesForUserCommand() {
        if (areaTypesForUserCommand == null) {
            areaTypesForUserCommand = new GetAreaTypesForUserCommand();
        }
        return areaTypesForUserCommand;
    }

    public void setAreaTypesForUserCommand(GetAreaTypesForUserCommand areaTypesForUserCommand) {
        this.areaTypesForUserCommand = areaTypesForUserCommand;
    }

    public GetStoresForCorporateCommand getStoresForCorporateCommand() {
        if (storesForCorporateCommand == null) {
            storesForCorporateCommand = new GetStoresForCorporateCommand();
        }
        return storesForCorporateCommand;
    }

    public void setStoresForCorporateCommand(GetStoresForCorporateCommand storesForCorporateCommand) {
        this.storesForCorporateCommand = storesForCorporateCommand;
    }

    public GetAreaTypesForCorporateCommand getAreaTypesForCorporateCommand() {
        if (areaTypesForCorporateCommand == null) {
            areaTypesForCorporateCommand = new GetAreaTypesForCorporateCommand();
        }
        return areaTypesForCorporateCommand;
    }

    public void setAreaTypesForCorporateCommand(GetAreaTypesForCorporateCommand areaTypesForCorporateCommand) {
        this.areaTypesForCorporateCommand = areaTypesForCorporateCommand;
    }

    public List<StoreDTO> getStoresForCorporate(Integer corporateId, long sessionId)
            throws ScopixException {
        log.info("start");
        checkSecurity(sessionId, SecurityManagerPermissions.GET_STORES_FOR_CORPORATE_PERMISSION);
        String userName = getUserName(sessionId);

        List<StoreDTO> list = getStoresForCorporateCommand().execute(corporateId, userName);

        log.info("end");
        return list;
    }

    public List<AreaTypeDTO> getAreaTypesForCorporate(Integer corporateId, long sessionId)
            throws ScopixException {
        log.info("start");
        checkSecurity(sessionId, SecurityManagerPermissions.GET_AREA_TYPES_FOR_CORPORATE_PERMISSION);
        String userName = getUserName(sessionId);

        List<AreaTypeDTO> list = getAreaTypesForCorporateCommand().execute(corporateId, userName);

        log.info("end");
        return list;
    }

    public List<StoreDTO> getStoresForUser(String userName, long sessionId)
            throws ScopixException {
        log.info("start");
        checkSecurity(sessionId, SecurityManagerPermissions.GET_STORES_FOR_USER_PERMISSION);

        List<StoreDTO> list = getStoresForUserCommand().execute(userName);

        log.info("end");
        return list;
    }

    public List<AreaTypeDTO> getAreaTypesForUser(String userName, long sessionId)
            throws ScopixException {
        log.info("start");
        checkSecurity(sessionId, SecurityManagerPermissions.GET_AREA_TYPES_FOR_USER_PERMISSION);

        List<AreaTypeDTO> list = getAreaTypesForUserCommand().execute(userName);

        log.info("end");
        return list;
    }
}
