package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.constant.ApplicationConstant;
import com.bolton.globalhotelhub.dto.ResponseDTO;
import com.bolton.globalhotelhub.dto.request.FilterHotelRequestDTO;
import com.bolton.globalhotelhub.entity.SearchHotelHistory;
import com.bolton.globalhotelhub.entity.Users;
import com.bolton.globalhotelhub.exception.GlobalHotelHubServiceException;
import com.bolton.globalhotelhub.repository.SearchHotelHistoryRepository;
import com.bolton.globalhotelhub.repository.UserRepository;
import com.bolton.globalhotelhub.service.HotelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            String BookingComUrl = String.format(ApplicationConstant.BOOKING_COM_URL, filterHotelRequestDTO.getLocation(), filterHotelRequestDTO.getCheckin(), filterHotelRequestDTO.getCheckout(), filterHotelRequestDTO.getAdults(), filterHotelRequestDTO.getRooms(), filterHotelRequestDTO.getChild(), filterHotelRequestDTO.getMinPrice(), filterHotelRequestDTO.getMaxPrice());
            String HotelComUrl = String.format(ApplicationConstant.HOTEL_COM_URL, filterHotelRequestDTO.getAdults(), filterHotelRequestDTO.getChild(), filterHotelRequestDTO.getCheckin(), filterHotelRequestDTO.getCheckout(), filterHotelRequestDTO.getLocation(), filterHotelRequestDTO.getRooms(), filterHotelRequestDTO.getMinPrice(), filterHotelRequestDTO.getMaxPrice());
            String AirBnbUrl = String.format(ApplicationConstant.AIR_BNB_URL, filterHotelRequestDTO.getLocation(), filterHotelRequestDTO.getAdults(), filterHotelRequestDTO.getChild(), filterHotelRequestDTO.getCheckin(), filterHotelRequestDTO.getCheckout(), filterHotelRequestDTO.getRooms(), filterHotelRequestDTO.getMinPrice(), filterHotelRequestDTO.getMaxPrice());

            LOGGER.debug(AirBnbUrl);

            extractDataFromBookingCom(responseDTOS, BookingComUrl);
//            extractDataFromHotelsCom(responseDTOS, HotelComUrl);
            extractDataFromAirBnb(responseDTOS, AirBnbUrl);

            Optional<Users> user = userRepository.findById(filterHotelRequestDTO.getUserId());

            if (!user.isPresent())
                throw new GlobalHotelHubServiceException(RESOURCE_NOT_FOUND, "User not found");

            SearchHotelHistory searchHotelHistory = new SearchHotelHistory();
            searchHotelHistory.setUsers(user.get());
            searchHotelHistory.setLocation(filterHotelRequestDTO.getLocation());
            searchHotelHistory.setCheckin(filterHotelRequestDTO.getCheckin());
            searchHotelHistory.setCheckout(filterHotelRequestDTO.getCheckout());
            searchHotelHistory.setRooms(filterHotelRequestDTO.getRooms());
            searchHotelHistory.setAdults(filterHotelRequestDTO.getAdults());
            searchHotelHistory.setChild(filterHotelRequestDTO.getChild());
            searchHotelHistory.setMaxPrice(filterHotelRequestDTO.getMaxPrice());
            searchHotelHistory.setMinPrice(filterHotelRequestDTO.getMinPrice());
            searchHotelHistory.setDateTime(new Date());

            searchHotelHistoryRepository.save(searchHotelHistory);

            return responseDTOS;

        } catch (Exception e) {
            LOGGER.error("Function : getHotelByModel  : " + e.getMessage());
            throw e;
        }
    }

    private void extractDataFromBookingCom(List<ResponseDTO> responseDTOS, String url) {
        try {

            Document document = Jsoup.connect(url).get();
            Elements mainItems = document.getElementsByClass("c82435a4b8");
            for (Element object : mainItems) {

                if (object.getElementsByClass("a15b38c233").text().length() > 0) {
                    ResponseDTO responseDTO = new ResponseDTO();

                    responseDTO.setTitle(object.getElementsByClass("a15b38c233").text());
                    responseDTO.setUrl(object.getElementsByTag("a").attr("href"));
                    responseDTO.setImage(object.getElementsByTag("img").attr("src"));
                    responseDTO.setAddress(object.getElementsByClass("def9bc142a").text());

                    String price = object.getElementsByClass("e84eb96b1f").text();
                    Pattern pattern = Pattern.compile("LKR\\s(\\d{1,3}(,\\d{3})*)(\\.\\d{1,2})?");
                    Matcher matcher = pattern.matcher(price);

                    if (matcher.find()) {
                        String matchedPrice = matcher.group(1);
                        responseDTO.setPrice("LKR " + matchedPrice);
                    } else {
                        responseDTO.setPrice("LKR " + price);
                    }

                    responseDTO.setAmenities(object.getElementsByClass("fc367255e6").text());
                    responseDTO.setPublishSite("booking.com");

                    responseDTOS.add(responseDTO);
                }


            }

        } catch (IOException ex) {
            LOGGER.error("Function : extractDataFromBookingCom  : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void extractDataFromHotelsCom(List<ResponseDTO> responseDTOS, String url) {
        try {

            Document document = Jsoup.connect(url).get();
            LOGGER.debug(document);
//            Elements mainItems = document.getElementsByClass("property-listing-results");
//            for (Element object : mainItems) {
//
//                LOGGER.debug(object.getElementsByClass("uitk-heading").text());
//                LOGGER.debug(object.getElementsByTag("a").attr("href"));
//                LOGGER.debug(object.getElementsByClass("def9bc142a").text());
//                LOGGER.debug(object.getElementsByClass("e84eb96b1f").text());
//                LOGGER.debug(object.getElementsByClass("fc367255e6").text());
//                LOGGER.debug(object.getElementsByTag("img").attr("src"));


//                ResponseDTO responseDTO = new ResponseDTO();
//
//                responseDTO.setTitle(object.getElementsByClass("a15b38c233").text());
//                responseDTO.setUrl(object.getElementsByTag("a").attr("href"));
//                responseDTO.setImage(object.getElementsByTag("img").attr("src"));
//                responseDTO.setAddress(object.getElementsByClass("def9bc142a").text());
//                responseDTO.setPrice(object.getElementsByClass("fc367255e6").text());
//                responseDTO.setAmenities(object.getElementsByClass("fc367255e6").text());
//                responseDTO.setPublishSite("booking.com");
////
//                responseDTOS.add(responseDTO);

//            }

        } catch (IOException ex) {
            LOGGER.error("Function : extractDataFromBookingCom  : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void extractDataFromAirBnb(List<ResponseDTO> responseDTOS, String url) {
        try {

            Document document = Jsoup.connect(url).get();

            String mainItems = document.select("script#data-deferred-state").html();

            if (mainItems != null && !mainItems.isEmpty()) {
                JSONObject jsonData = new JSONObject(mainItems);


                JSONArray niobeMinimalClientDataArray = jsonData.getJSONArray("niobeMinimalClientData");

                // Check if the array has elements
                if (niobeMinimalClientDataArray.length() > 0) {
                    // Access the first element of the array
                    JSONArray firstElementArray = niobeMinimalClientDataArray.getJSONArray(0);

                    // Check if the first element array has elements
                    if (firstElementArray.length() > 1) {
                        // Access the second element of the first element array (which is a nested JSON object)
                        JSONObject nestedJsonObject = firstElementArray.getJSONObject(1);

                        // Check if the nested JSON object has a 'data' property
                        if (nestedJsonObject.has("data")) {
                            // Access the 'data' object within the nested JSON structure
                            JSONObject dataObject = nestedJsonObject.getJSONObject("data");

                            // Check if the 'data' object has a 'presentation' property
                            if (dataObject.has("presentation")) {
                                // Access the 'presentation' object within the 'data' object
                                JSONObject presentationObject = dataObject.getJSONObject("presentation");

                                // Check if the 'presentation' object has a 'staysSearch' property
                                if (presentationObject.has("staysSearch")) {
                                    // Access the 'staysSearch' object within the 'presentation' object
                                    JSONObject staysSearchObject = presentationObject.getJSONObject("staysSearch");

                                    // Check if the 'staysSearch' object has a 'results' property
                                    if (staysSearchObject.has("results")) {
                                        // Access the 'results' object within the 'staysSearch' object
                                        JSONObject resultsObject = staysSearchObject.getJSONObject("results");

                                        // Check if the 'results' object has a 'searchResults' property
                                        if (resultsObject.has("searchResults")) {
                                            // Access the 'searchResults' array within the 'results' object
                                            JSONArray searchResultsArray = resultsObject.getJSONArray("searchResults");

                                            for (Object object : searchResultsArray) {

                                                JSONObject jsonDataObj = (JSONObject) object;

                                                JSONObject result = jsonDataObj.getJSONObject("listing");
                                                String bodyValue = "1 bed";
                                                String price = "";

                                                Object pictureArray = result.getJSONArray("contextualPictures");

                                                JSONObject picObject = ((JSONArray) pictureArray).getJSONObject(0);

                                                JSONObject pricingQuoteObj = (JSONObject) object;
                                                JSONObject pricingQuote = pricingQuoteObj.getJSONObject("pricingQuote");

                                                JSONObject priceObj = pricingQuote.getJSONObject("structuredStayDisplayPrice");


                                                JSONObject pricePrimaryLine = priceObj.getJSONObject("primaryLine");

                                                if (pricePrimaryLine.has("price")) {
                                                    price = pricePrimaryLine.getString("price");
                                                } else {
                                                    price = pricePrimaryLine.getString("discountedPrice");
                                                }


                                                try {
                                                    JSONObject structuredObj = ((JSONObject) result).getJSONObject("structuredContent");

                                                    if (structuredObj.has("primaryLine")) {
                                                        Object primaryLineObject = structuredObj.get("primaryLine");

                                                        if (primaryLineObject instanceof JSONArray) {
                                                            // It's an array, handle it as such
                                                            JSONArray primaryLine = (JSONArray) primaryLineObject;

                                                            if (primaryLine.length() > 0) {
                                                                JSONObject jsonObject = primaryLine.getJSONObject(0);

                                                                if (jsonObject.has("body")) {
                                                                    bodyValue = jsonObject.getString("body");
                                                                }
                                                            }
                                                        } else if (primaryLineObject instanceof JSONObject) {
                                                            // It's an object, handle it as such
                                                            JSONObject jsonObject = (JSONObject) primaryLineObject;

                                                            if (jsonObject.has("body")) {
                                                                bodyValue = jsonObject.getString("body");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                ResponseDTO responseDTO = new ResponseDTO();

                                                responseDTO.setTitle(result.getString("title"));
                                                responseDTO.setUrl("#");
                                                responseDTO.setImage(picObject.getString("picture"));
                                                responseDTO.setAddress(result.getString("name"));
                                                responseDTO.setPrice(price);
                                                responseDTO.setAmenities(bodyValue);
                                                responseDTO.setPublishSite("airbnb.com");
//
                                                responseDTOS.add(responseDTO);
                                            }

                                            // Use the 'searchResults' array as needed
//                                        System.out.println(searchResultsArray.toString());
                                        } else {
                                            System.out.println("'searchResults' array not found in 'results' object");
                                        }
                                    } else {
                                        System.out.println("'results' object not found in 'staysSearch' object");
                                    }
                                } else {
                                    System.out.println("'staysSearch' object not found in 'presentation' object");
                                }
                            } else {
                                System.out.println("'presentation' object not found in 'data' object");
                            }
                        } else {
                            System.out.println("'data' object not found in nested JSON");
                        }
                    } else {
                        System.out.println("Not enough elements in the first element array");
                    }
                } else {
                    System.out.println("No elements found in 'niobeMinimalClientData' array");
                }
            } else {
                System.out.println("mainItems is null or empty");
            }


        } catch (IOException ex) {
            LOGGER.error("Function : extractDataFromAirbnb : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
