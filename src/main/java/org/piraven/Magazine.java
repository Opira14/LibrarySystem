package org.piraven;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Magazine extends Item {
    private int issueNumber;
    private String publisher;
}
