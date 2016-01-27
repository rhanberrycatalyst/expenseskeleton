package com.expensereport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.expensereport.domain.Lineitem;
import com.expensereport.repository.LineitemRepository;
import com.expensereport.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lineitem.
 */
@RestController
@RequestMapping("/api")
public class LineitemResource {

    private final Logger log = LoggerFactory.getLogger(LineitemResource.class);
        
    @Inject
    private LineitemRepository lineitemRepository;
    
    /**
     * POST  /lineitems -> Create a new lineitem.
     */
    @RequestMapping(value = "/lineitems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> createLineitem(@Valid @RequestBody Lineitem lineitem) throws URISyntaxException {
        log.debug("REST request to save Lineitem : {}", lineitem);
        if (lineitem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lineitem", "idexists", "A new lineitem cannot already have an ID")).body(null);
        }
        Lineitem result = lineitemRepository.save(lineitem);
        return ResponseEntity.created(new URI("/api/lineitems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lineitem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lineitems -> Updates an existing lineitem.
     */
    @RequestMapping(value = "/lineitems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> updateLineitem(@Valid @RequestBody Lineitem lineitem) throws URISyntaxException {
        log.debug("REST request to update Lineitem : {}", lineitem);
        if (lineitem.getId() == null) {
            return createLineitem(lineitem);
        }
        Lineitem result = lineitemRepository.save(lineitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lineitem", lineitem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lineitems -> get all the lineitems.
     */
    @RequestMapping(value = "/lineitems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lineitem> getAllLineitems() {
        log.debug("REST request to get all Lineitems");
        return lineitemRepository.findAll();
            }

    /**
     * GET  /lineitems/:id -> get the "id" lineitem.
     */
    @RequestMapping(value = "/lineitems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lineitem> getLineitem(@PathVariable Long id) {
        log.debug("REST request to get Lineitem : {}", id);
        Lineitem lineitem = lineitemRepository.findOne(id);
        return Optional.ofNullable(lineitem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lineitems/:id -> delete the "id" lineitem.
     */
    @RequestMapping(value = "/lineitems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLineitem(@PathVariable Long id) {
        log.debug("REST request to delete Lineitem : {}", id);
        lineitemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lineitem", id.toString())).build();
    }
}
