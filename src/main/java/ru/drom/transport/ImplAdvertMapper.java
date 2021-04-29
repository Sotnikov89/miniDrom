package ru.drom.transport;

import ru.drom.dto.DTOAdvert;
import ru.drom.model.Advert;

import java.util.List;
import java.util.stream.Collectors;

public class ImplAdvertMapper implements AdvertMapper{

    @Override
    public DTOAdvert advertToDtoAdvert(Advert advert) {
        return DTOAdvert.builder()
                .id(advert.getId())
                .created(advert.getCreated())
                .make(advert.getModel().getMake().getName())
                .description(advert.getDescription())
                .mileage(advert.getMileage())
                .photoId(advert.getPhotoId())
                .price(advert.getPrice())
                .user(advert.getUser())
                .status(advert.isSold())
                .typeBody(advert.getTypeBody())
                .model(advert.getModel())
                .yearOfIssue(advert.getYearOfIssue())
                .build();
    }

    @Override
    public List<DTOAdvert> advertsToDtoAdverts(List<Advert> adverts) {
        return adverts.stream().map(this::advertToDtoAdvert).collect(Collectors.toList());
    }
}
