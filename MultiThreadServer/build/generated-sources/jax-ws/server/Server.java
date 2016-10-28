
package server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Server", targetNamespace = "http://server/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Server {


    /**
     * 
     * @param port
     * @param latitude
     * @param iPaddress
     * @param username
     * @param longitude
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "register", targetNamespace = "http://server/", className = "server.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://server/", className = "server.RegisterResponse")
    @Action(input = "http://server/Server/registerRequest", output = "http://server/Server/registerResponse")
    public Boolean register(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "latitude", targetNamespace = "")
        double latitude,
        @WebParam(name = "longitude", targetNamespace = "")
        double longitude,
        @WebParam(name = "IPaddress", targetNamespace = "")
        String iPaddress,
        @WebParam(name = "port", targetNamespace = "")
        int port);

    /**
     * 
     * @param username
     * @return
     *     returns server.UserAddress
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "searchNeighbour", targetNamespace = "http://server/", className = "server.SearchNeighbour")
    @ResponseWrapper(localName = "searchNeighbourResponse", targetNamespace = "http://server/", className = "server.SearchNeighbourResponse")
    @Action(input = "http://server/Server/searchNeighbourRequest", output = "http://server/Server/searchNeighbourResponse")
    public UserAddress searchNeighbour(
        @WebParam(name = "username", targetNamespace = "")
        String username);

    /**
     * 
     * @param averageValue
     * @param parameter
     * @param username
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "storeMeasurement", targetNamespace = "http://server/", className = "server.StoreMeasurement")
    @ResponseWrapper(localName = "storeMeasurementResponse", targetNamespace = "http://server/", className = "server.StoreMeasurementResponse")
    @Action(input = "http://server/Server/storeMeasurementRequest", output = "http://server/Server/storeMeasurementResponse")
    public Boolean storeMeasurement(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "parameter", targetNamespace = "")
        String parameter,
        @WebParam(name = "averageValue", targetNamespace = "")
        float averageValue);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "calculateDistance", targetNamespace = "http://server/", className = "server.CalculateDistance")
    @ResponseWrapper(localName = "calculateDistanceResponse", targetNamespace = "http://server/", className = "server.CalculateDistanceResponse")
    @Action(input = "http://server/Server/calculateDistanceRequest", output = "http://server/Server/calculateDistanceResponse")
    public double calculateDistance(
        @WebParam(name = "arg0", targetNamespace = "")
        UserAddress arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        double arg2);

}
