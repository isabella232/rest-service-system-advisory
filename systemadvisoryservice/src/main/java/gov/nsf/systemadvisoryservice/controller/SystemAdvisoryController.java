package gov.nsf.systemadvisoryservice.controller;

import gov.mynsf.common.datetime.NsfDateTimeUtil;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.ember.model.EmberModel;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;
import gov.nsf.systemadvisoryservice.model.SystemAdvisoryWrapper;
import gov.nsf.systemadvisoryservice.service.SystemAdvisoryService;
import gov.nsf.systemadvisoryservice.validator.SystemAdvisoryValidatorFactory;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * SystemAdvisoryController - Controller to serve /systemAdvisory requests
 * 
 * @author jlinden
 * 
 */
@RestController
public class SystemAdvisoryController extends SystemAdvisoryBaseController {
	@Autowired
	private SystemAdvisoryService systemAdvisoryService;

	/**
	 * GET handler for /systemAdvisories URL Parameters: active - true / false
	 * (defaults to true) applicationNames - CSV list of application names
	 * (defaults to empty)
	 * 
	 * Example URLs:
	 * /systemAdvisories?active=false&applicationNames=eJacket,MyNSF
	 * /systemAdvisories /systemAdvisories?applicationNames=MyNSF
	 * 
	 * @return EmberModel object of type List<SystemAdvisory>
	 * @throws RollbackException
	 */
	@RequestMapping(value = "/systemAdvisories",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EmberModel list(@RequestParam(value = "active", defaultValue = "ALL") String activeOnly, @RequestParam(value = "applicationNames", defaultValue = "") List<String> applicationNames) throws RollbackException {
		Collection<SystemAdvisory> sysAdvisoryList = systemAdvisoryService.getSystemAdvisories(applicationNames, activeOnly);
		return new EmberModel.ModelBuilder<SystemAdvisory>(Constants.SYSTEM_ADVISORY, sysAdvisoryList).build();
	}

	/**
	 * POST handler for /systemAdvisories Request body should contain a properly
	 * formatted JSON corresponding to the SystemAdvisory class
	 * 
	 * @throws RollbackException
	 * @throws FormValidationException
	 * @return EmberModel object of type SystemAdvisoryWrapper
	 */
	@PreAuthorize("hasPermission(null, 'mynsf-system-advisory.create')")
	@RequestMapping(value = "/systemAdvisories",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EmberModel create(@RequestBody SystemAdvisory systemAdvisory) throws RollbackException, FormValidationException {
		SystemAdvisoryValidatorFactory.getValidator(RequestMethod.POST).validateRequest(systemAdvisory);
		systemAdvisory.setAdvisoryEffectiveDate(NsfDateTimeUtil.convertToSybaseDateTimeString(systemAdvisory.getAdvisoryEffectiveDate()));
		systemAdvisory.setAdvisoryExpiryDate(NsfDateTimeUtil.convertToSybaseDateTimeString(systemAdvisory.getAdvisoryExpiryDate()));

		// add validation from data (eff date should be less than current date)
		SystemAdvisoryWrapper wrapper = systemAdvisoryService.createSystemAdvisory(systemAdvisory);
		return new EmberModel.ModelBuilder<SystemAdvisoryWrapper>(Constants.SYSTEM_ADVISORY_WRAPPER, wrapper).build();
	}

	/**
	 * Edit handler for /systemAdvisories Request body should contain a properly
	 * formatted JSON corresponding to the SystemAdvisory class
	 * 
	 * @throws RollbackException
	 * @throws FormValidationException
	 * @return EmberModel object of type SystemAdvisoryWrapper
	 */
	@PreAuthorize("hasPermission(null, 'mynsf-system-advisory.edit')")
	@RequestMapping(value = "/systemAdvisories/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EmberModel edit(@RequestBody SystemAdvisory systemAdvisory, @PathVariable String id) throws RollbackException, FormValidationException {
		systemAdvisory.setId(id);
		SystemAdvisoryValidatorFactory.getValidator(RequestMethod.PUT).validateRequest(systemAdvisory);
		systemAdvisory.setAdvisoryEffectiveDate(NsfDateTimeUtil.convertToSybaseDateTimeString(systemAdvisory.getAdvisoryEffectiveDate()));
		systemAdvisory.setAdvisoryExpiryDate(NsfDateTimeUtil.convertToSybaseDateTimeString(systemAdvisory.getAdvisoryExpiryDate()));
		
		SystemAdvisoryWrapper wrapper = systemAdvisoryService.editSystemAdvisory(systemAdvisory);
		return new EmberModel.ModelBuilder<SystemAdvisoryWrapper>(Constants.SYSTEM_ADVISORY_WRAPPER, wrapper).build();
	}

	/**
	 * Archive handler for /systemAdvisories Request body should contain a
	 * properly formatted JSON corresponding to the SystemAdvisory class
	 * 
	 * @throws RollbackException
	 * @throws FormValidationException
	 * @return EmberModel object of type SystemAdvisoryWrapper
	 */
	@PreAuthorize("hasPermission(null, 'mynsf-system-advisory.delete')")
	@RequestMapping(value = "/systemAdvisories/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EmberModel archive(@RequestBody SystemAdvisory systemAdvisory, @PathVariable String id) throws RollbackException, FormValidationException {
		systemAdvisory.setId(id);
		SystemAdvisoryValidatorFactory.getValidator(RequestMethod.DELETE).validateRequest(systemAdvisory);
		systemAdvisory.setAdvisoryExpiryDate(NsfDateTimeUtil.convertToSybaseDateTimeString(systemAdvisory.getAdvisoryExpiryDate()));

		SystemAdvisoryWrapper wrapper = systemAdvisoryService.archiveSystemAdvisory(systemAdvisory);
		return new EmberModel.ModelBuilder<SystemAdvisoryWrapper>(Constants.SYSTEM_ADVISORY_WRAPPER, wrapper).build();
	}

}
