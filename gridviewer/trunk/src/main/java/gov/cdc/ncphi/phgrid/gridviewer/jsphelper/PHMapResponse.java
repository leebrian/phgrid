/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.Date;
import java.util.List;

/**
 *
 * @author cmcilvoy
 */
public class PHMapResponse {

    private List<RegionalObservations> regionalObservations;
    private Date serviceCallRequestTime;
    private Date serviceCallResponseTime;

    /**
     * @return the regionalObservations
     */
    public List<RegionalObservations> getRegionalObservations() {
        return regionalObservations;
    }

    /**
     * @param regionalObservations the regionalObservations to set
     */
    public void setRegionalObservations(List<RegionalObservations> regionalObservations) {
        this.regionalObservations = regionalObservations;
    }

    /**
     * @return the serviceCallRequestTime
     */
    public Date getServiceCallRequestTime() {
        return serviceCallRequestTime;
    }

    /**
     * @param serviceCallRequestTime the serviceCallRequestTime to set
     */
    public void setServiceCallRequestTime(Date serviceCallRequestTime) {
        this.serviceCallRequestTime = serviceCallRequestTime;
    }

    /**
     * @return the serviceCallResponseTime
     */
    public Date getServiceCallResponseTime() {
        return serviceCallResponseTime;
    }

    /**
     * @param serviceCallResponseTime the serviceCallResponseTime to set
     */
    public void setServiceCallResponseTime(Date serviceCallResponseTime) {
        this.serviceCallResponseTime = serviceCallResponseTime;
    }


}
