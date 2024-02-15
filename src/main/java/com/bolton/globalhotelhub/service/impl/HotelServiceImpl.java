package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.constant.ApplicationConstant;
import com.bolton.globalhotelhub.dto.ResponseDTO;
import com.bolton.globalhotelhub.dto.request.FilterHotelRequestDTO;
import com.bolton.globalhotelhub.entity.Users;
import com.bolton.globalhotelhub.exception.GlobalHotelHubServiceException;
import com.bolton.globalhotelhub.repository.SearchHotelHistoryRepository;
import com.bolton.globalhotelhub.repository.UserRepository;
import com.bolton.globalhotelhub.service.HotelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bolton.globalhotelhub.constant.ErrorCodeConstant.RESOURCE_NOT_FOUND;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HotelServiceImpl implements HotelService {
    private static final Logger LOGGER = LogManager.getLogger(HotelServiceImpl.class);
    private final UserRepository userRepository;
    private final SearchHotelHistoryRepository searchHotelHistoryRepository;

    @Autowired
    public HotelServiceImpl(UserRepository userRepository, SearchHotelHistoryRepository searchHotelHistoryRepository) {
        this.userRepository = userRepository;
        this.searchHotelHistoryRepository = searchHotelHistoryRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ResponseDTO> getHotelByModel(FilterHotelRequestDTO filterHotelRequestDTO) {
        try {

            List<ResponseDTO> responseDTOS = new ArrayList<>();

            String BookingComUrl = String.format(ApplicationConstant.BOOKING_COM_URL, filterHotelRequestDTO.getLocation(), filterHotelRequestDTO.getCheckin(), filterHotelRequestDTO.getCheckout(), filterHotelRequestDTO.getAdults(), filterHotelRequestDTO.getRooms(), filterHotelRequestDTO.getChild(), filterHotelRequestDTO.getMaxPrice(), filterHotelRequestDTO.getMinPrice());

            LOGGER.debug(BookingComUrl);

            extractDataFromBookingCom(responseDTOS, BookingComUrl);
//
//            Optional<Users> user = userRepository.findById(filterHotelRequestDTO.getUserId());
//
//            if(!user.isPresent())
//                throw new GlobalHotelHubServiceException(RESOURCE_NOT_FOUND, "User not found");


            return responseDTOS;

        } catch (Exception e) {
            LOGGER.error("Function : getHotelByModel  : " + e.getMessage());
            throw e;
        }
    }

    private void extractDataFromBookingCom(List<ResponseDTO> responseDTOS, String url) {
        try {

            Document document = Jsoup.connect(url).get();
//            LOGGER.debug(document);
            Elements mainItems = document.getElementsByClass("c82435a4b8");
            for (Element object : mainItems) {

                LOGGER.debug(object.getElementsByClass("a15b38c233").text());
                LOGGER.debug(object.getElementsByTag("a").attr("href"));
                LOGGER.debug(object.getElementsByClass("def9bc142a").text());
                LOGGER.debug(object.getElementsByClass("e84eb96b1f").text());
                LOGGER.debug(object.getElementsByClass("fc367255e6").text());
                LOGGER.debug(object.getElementsByTag("img").attr("src"));


                ResponseDTO responseDTO = new ResponseDTO();
//
                responseDTO.setTitle(object.getElementsByClass("a15b38c233").text());
                responseDTO.setUrl(object.getElementsByTag("a").attr("href"));
                responseDTO.setImage(object.getElementsByTag("img").attr("src"));
                responseDTO.setAddress(object.getElementsByClass("def9bc142a").text());
                responseDTO.setPrice(object.getElementsByClass("fc367255e6").text());
                responseDTO.setAmenities(object.getElementsByClass("fc367255e6").text());
                responseDTO.setPublishSite("booking.com");
//
                responseDTOS.add(responseDTO);

            }

        } catch (IOException ex) {
            LOGGER.error("Function : extractDataFromBookingCom  : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
