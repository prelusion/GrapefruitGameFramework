package com.grapefruit.gamework.app.view.templates;
import com.grapefruit.gamework.app.model.IModel;


public interface TemplateFactory {

     /**
      *
      * @param model for controller
      * @return Template
      */
     Template buildTemplate(IModel model);


}
