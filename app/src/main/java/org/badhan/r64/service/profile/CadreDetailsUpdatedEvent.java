package org.badhan.r64.service.profile;

import org.badhan.r64.entity.Cadre;

public class CadreDetailsUpdatedEvent {
    public Cadre cadre;

    public CadreDetailsUpdatedEvent(Cadre cadre) {
        this.cadre = cadre;
    }
}
