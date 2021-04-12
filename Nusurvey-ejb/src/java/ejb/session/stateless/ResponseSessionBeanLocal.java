/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SurveyResponse;
import javax.ejb.Local;

/**
 *
 * @author miche
 */
@Local
public interface ResponseSessionBeanLocal {
    public long createResponse(SurveyResponse newResponse);
}
