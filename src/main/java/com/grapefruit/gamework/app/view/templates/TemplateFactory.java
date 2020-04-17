package com.grapefruit.gamework.app.view.templates;
import com.grapefruit.gamework.app.model.IModel;


/**
 * The interface Template factory.
 */
public interface TemplateFactory {

     /**
      * Build template template.
      *
      * @param model for controller
      * @return Template template
      */
     Template buildTemplate(IModel model);


}
