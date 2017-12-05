/*********************************************************************************************
 * PureClearButton. v0.1-beta
 * ===========================================================================================
 * homepage: http://pureopensource.github.io/pureClearButton/
 * 
 * Copyright 2015 Pure OpenSource.
 * Licensed under MIT (https://github.com/PureOpenSource/pureClearButton/blob/master/LICENSE)
 *********************************************************************************************/

+function(){
	'use strict';
	
	/**
	 * Pure Clear Button - Class Definition.
	 */
	
	var PureClearButton = function(element){
		this.$element = $(element);
		this.$clearButton = null;
	}
	
	PureClearButton.VERSION = '0.1-beta';
	
	PureClearButton.DEFIN = {
		clearClickEvent: 'click.pure.button',
		clearButton: '[data-pure-clear-button^="event"]'
	}
	
	PureClearButton.prototype.create = function(){
		if(this.$clearButton){
			return;
		}
		var element = this.$element;
		var options = PureClearButton.DEFAULTS;
		
		if(!element.closest('.form-group').hasClass('has-feedback')){
			element.closest('.form-group').addClass('has-feedback')
		}
		
		// Clear Button? input element ?? ????.
		element.css({'padding-right': '30px'});
		element.attr('data-pure-clear-button', 'true');
		
		this.$clearButton = $('<span></span>')
			.attr('aria-hidden', 'true')
			.addClass('glyphicon ' + $.pureClearButton.DEFAULTS.icon + ' form-control-feedback')
			.attr('data-pure-clear-button', 'event')
			.css({'cursor': 'pointer', 'pointer-events': 'auto', 'right': '0px'});
		element.after(this.$clearButton);
	}
	
	PureClearButton.prototype.destroy = function(){
		var element = this.$element;
		
		element.attr('data-pure-clear-button', 'false');
		
		// Clear Button? ??? ????.
		this.$clearButton.remove();
		this.$clearButton = null;
	}
	
	PureClearButton.prototype.show = function(){
		if(this.$clearButton){
			this.$clearButton.css({'display': 'block'});
		}
	}
	
	PureClearButton.prototype.hide = function(){
		if(this.$clearButton){
			this.$clearButton.css({'display': 'none'});
		}
	}	

	
	/**
	 * Pure Clear Button - Plug-in Definition.
	 */
	
	function Plugin(option){
		var $this = $(this);
		
		var data = $this.data('pure.pureClearButton');
		var options = $.extend({}, $this.data);
		
		if(!data) $this.data('pure.pureClearButton', (data = new PureClearButton(this)));
		if(typeof option == 'string'){
			var returnValue = data[option]();
			return returnValue == undefined ? $this : returnValue; 
		}
	}
	
	var old = $.fn.pureClearButton; 
	
	$.pureClearButton = function(options){
		
		this.options = $.extend(true, {}, $.pureClearButton.DEFAULTS, options);
	}
	
	$.extend($.pureClearButton, {
		DEFAULTS: {
			// jquery-validation? ????? ?? clear?? valid ?? ?? ??
			clearValidation: true,			
			// Clear Button? icon with bootstrap
			icon: 'glyphicon-remove-sign',				
		},
		
		setDefault: function(settings){
			$.extend($.pureClearButton.DEFAULTS, settings);
		}
	});

	$.fn.pureClearButton = Plugin;
	$.fn.pureClearButton.Constructor = PureClearButton;
	
	/**
	 * Pure Clear Button - no Conflict
	 */
	
	$.fn.pureClearButton.noConflict = function(){
		$.fn.pureClearButton = old;
		return this;
	}
	
	/**
	 * Pure Clear Button - Event
	 */
	$(document).on('click', PureClearButton.DEFIN.clearButton, function(e){
		var $btn = $(e.target);
		var $input = $btn.parent().find('input');
		$input.val("").focus();
		/*
		var $input = $(this).closest(".form-group").find("input");
		
		// input value ??, ??? ??
		$input.val("").focus();
		console.log($input.data('pure.pureClearButton'));
		if($input.data('pure.pureClearButton').options.clearValidation && $.validator){
			// validation tooltip, class ??
			$input.removeData("title").tooltip("destroy");
			$input.closest('.form-group').removeClass('has-error has-success');
		}*/
		
	});
	
	$(window).on('load', function(){
		// ".has-text-clear" Class? ??? Input Text? ?? ?? ??? ????.
		$.each($("input:text, input:password, " +
				"input[type='email'], input[type='url'], input[type='search'], input[type='tel'], input[type='number'], " +
				"input[type='datetime']"), //, input[type='datetime-local'], input[type='date'], input[type='month'], input[type='time'], input[type='week']
			function(index, element){
			var $element = $(element);
			
			if(($element.attr("data-pure-clear-button") == undefined || $element.attr("data-pure-clear-button") === 'false') && !$element.hasClass("has-pure-clear-button")){
				return;
			} 
			Plugin.call($element, 'create');
		});		
	});
	
}(jQuery);