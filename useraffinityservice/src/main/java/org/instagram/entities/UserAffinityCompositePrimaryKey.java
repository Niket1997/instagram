package org.instagram.entities;

import org.instagram.enums.AffinityState;

import java.io.Serializable;

public class UserAffinityCompositePrimaryKey implements Serializable {
    private Long source;
    private AffinityState state;
    private Long position;
}
