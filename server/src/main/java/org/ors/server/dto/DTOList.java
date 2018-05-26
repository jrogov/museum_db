package org.ors.server.dto;

import org.ors.server.dto.IDTO;

import java.util.ArrayList;
import java.util.Collection;

/*
* IDTO for multiple objects. Used for simple ResponseEntity<IDTO> as a return type.
* */

public class DTOList<E> extends ArrayList<E> implements IDTO {
    public DTOList(int initialCapacity) {
        super(initialCapacity);
    }

    public DTOList() {
    }

    public DTOList(Collection c) {
        super(c);
    }
}
