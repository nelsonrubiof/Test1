/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.admin.controllers;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import com.scopix.periscope.securitymanagement.commands.ExportUsersToJoomlaCommand;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author nelson
 */
@SpringBean
public class JoomlaController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String corporateName = request.getParameter("corporate");
        if (corporateName == null || corporateName.length() == 0) {
            throw new ScopixException("No se recibe corporate a exportar");
        }
        ExportUsersToJoomlaCommand command = new ExportUsersToJoomlaCommand();
        command.execute(corporateName);

        return new ModelAndView(new XMLView());

    }
}
