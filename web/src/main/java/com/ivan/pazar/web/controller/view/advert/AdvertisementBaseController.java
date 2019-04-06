package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.exceptions.advertisement.AdvertisementNotFoundException;
import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/adverts")
@ControllerAdvice
public abstract class AdvertisementBaseController extends BaseController {

    @ExceptionHandler(value = AdvertisementNotFoundException.class)
    public ModelAndView advertisementNotFound() {
        return null;
    }
}
