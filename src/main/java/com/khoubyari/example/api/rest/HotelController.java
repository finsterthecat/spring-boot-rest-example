package com.khoubyari.example.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.exception.DataFormatException;
import com.khoubyari.example.service.HotelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/example/v1/hotels")
@Api(tags = {"hotels"})
public class HotelController extends AbstractRestHandler {

    @Autowired
    private HotelService hotelService;

    //TODO Check natural key for duplicates - Name plus City cannot be the same?
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new Hotel in the Location header.")
    public void createHotel(@RequestBody Hotel hotel,
                                 HttpServletRequest request, HttpServletResponse response) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdHotel.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Hotel> allHotel(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.hotelService.getAllHotels(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single hotel.", notes = "You have to provide a valid hotel ID.")
    @ApiResponses(value = {
    		@ApiResponse(code=404, message="Not found.")
    })
    public
    @ResponseBody
    Hotel singleHotel(@ApiParam(value = "The ID of the hotel.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Hotel hotel = this.hotelService.getHotel(id);
        //todo: http://goo.gl/6iNAkz
        return hotel;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update an existing hotel resource.",
    				notes = "You have to provide a valid hotel ID in the URL. The hotel with this id is always the one updated.")
    @ApiResponses(value = {
    		@ApiResponse(code=400, message="Id is different than hotel's id"),
    		@ApiResponse(code=404, message="Not found")
    })
    public void updateHotel(@ApiParam(value = "The ID of the hotel resource.", required = true)
    							@PathVariable("id") Long id,
    							@RequestBody Hotel hotel,
                                 HttpServletRequest request, HttpServletResponse response) {
        if (id != hotel.getId()) {
        		throw new DataFormatException("Cannot update when id is different than hotel's id");
        }
        this.hotelService.updateHotel(hotel);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a hotel resource.", notes = "Provide hotel ID in the URL. Once deleted the resource can not be recovered.")
    @ApiResponses(value = {
    		@ApiResponse(code=404, message="Not found")
    })
    public void deleteHotel(@ApiParam(value = "The ID of the hotel to delete.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        this.hotelService.deleteHotel(id);
    }
}
