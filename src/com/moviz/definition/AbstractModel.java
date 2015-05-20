package com.moviz.definition;

import com.moviz.service.DataPersister;

/**
 * Abtract model
 * Provide a default access to the DataPersister
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class AbstractModel {

    protected DataPersister persister;

    public AbstractModel(DataPersister persister) {
        this.persister = persister;
    }

}
