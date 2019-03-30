package com.ivan.pazar.web.controller.view.town;

import com.ivan.pazar.persistence.model.service.TownAddServiceModel;
import com.ivan.pazar.persistence.service.api.TownService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.TownAddBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class TownAddController extends TownBaseController {

    private final TownService townService;
    private final ModelMapper modelMapper;

    @Autowired
    public TownAddController(TownService townService, ModelMapper modelMapper) {
        this.townService = townService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute(WebConstants.TOWN)
    public TownAddBindingModel townAddBindingModel() {
        return new TownAddBindingModel();
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView addTown(Model model) {

        return renderView(WebConstants.VIEWS_ADD_TOWN, model);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView addTownConfirm(@Valid @ModelAttribute(WebConstants.TOWN) TownAddBindingModel townAddBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_ADD_TOWN, model);
        }

        townService.save(modelMapper.map(townAddBindingModel, TownAddServiceModel.class));

        return redirect(WebConstants.REDIRECT_INDEX);
    }

}
