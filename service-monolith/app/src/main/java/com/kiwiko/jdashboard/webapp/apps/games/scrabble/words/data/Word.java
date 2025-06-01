package com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Word extends DataEntityDTO {
    private Long id;
    private String word;
}
