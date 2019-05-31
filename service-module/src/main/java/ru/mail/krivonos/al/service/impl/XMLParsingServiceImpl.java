package ru.mail.krivonos.al.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.service.XMLParsingService;
import ru.mail.krivonos.al.service.exceptions.ItemParsingException;
import ru.mail.krivonos.al.service.model.ItemDTO;
import ru.mail.krivonos.al.service.model.ItemsDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

@Service("xmlParsingService")
public class XMLParsingServiceImpl implements XMLParsingService {

    private static final String ITEMS_PARSING_EXCEPTION_MESSAGE = "Error while parsing items from xml file";

    private static final Logger logger = LoggerFactory.getLogger(XMLParsingServiceImpl.class);

    @Override
    public List<ItemDTO> getItems(InputStream fileContent) {
        try {
            JAXBContext context = JAXBContext.newInstance(ItemsDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ItemsDTO itemsDTO = (ItemsDTO) unmarshaller.unmarshal(fileContent);
            return itemsDTO.getItems();
        } catch (JAXBException e) {
            logger.error(e.getMessage(), e);
            throw new ItemParsingException(ITEMS_PARSING_EXCEPTION_MESSAGE);
        }
    }
}
