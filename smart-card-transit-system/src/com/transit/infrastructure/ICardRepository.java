package com.transit.infrastructure;

import com.transit.domain.SmartCard;

public interface ICardRepository {
    void save(SmartCard card);
    SmartCard findById(String cardId);
}