package spring.mvc.demo.RestFuls;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.mvc.demo.Models.StateModel;
import spring.mvc.demo.Repository.CityRepository;
import spring.mvc.demo.Repository.StateRepository;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/Api")
public class CityStateApi {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;

    @RequestMapping(value = "/Cities", method = RequestMethod.GET)
    public String GetCities() {
        return new Gson().toJson(cityRepository.findAll());
    }

    @RequestMapping(value = "/States", method = RequestMethod.GET)
    public String GetStates(@RequestParam("id") int id) {
        return new Gson().toJson(stateRepository.GetById(id));
    }
}
