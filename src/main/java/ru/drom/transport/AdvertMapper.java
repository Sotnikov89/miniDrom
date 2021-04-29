package ru.drom.transport;

import ru.drom.dto.DTOAdvert;
import ru.drom.model.Advert;

import java.util.List;

public interface AdvertMapper {
    DTOAdvert advertToDtoAdvert(Advert advert);

    List<DTOAdvert> advertsToDtoAdverts(List<Advert> adverts);
}
