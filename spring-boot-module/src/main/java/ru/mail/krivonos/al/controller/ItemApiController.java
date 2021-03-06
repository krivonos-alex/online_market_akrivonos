package ru.mail.krivonos.al.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.krivonos.al.controller.validator.ItemValidator;
import ru.mail.krivonos.al.service.ItemService;
import ru.mail.krivonos.al.service.model.ArticleDTO;
import ru.mail.krivonos.al.service.model.ItemDTO;

import java.util.List;

import static ru.mail.krivonos.al.controller.constant.ApiURLConstants.API_ITEMS_URL;
import static ru.mail.krivonos.al.controller.constant.ApiURLConstants.API_ITEMS_WITH_ID_URL;
import static ru.mail.krivonos.al.controller.constant.PathVariableConstants.ID_VARIABLE;
import static ru.mail.krivonos.al.controller.constant.RequestParameterConstants.LIMIT_PARAMETER;
import static ru.mail.krivonos.al.controller.constant.RequestParameterConstants.OFFSET_PARAMETER;

@RestController("itemApiController")
public class ItemApiController {

    private final ItemService itemService;
    private final ItemValidator itemValidator;

    @Autowired
    public ItemApiController(
            ItemService itemService,
            ItemValidator itemValidator
    ) {
        this.itemService = itemService;
        this.itemValidator = itemValidator;
    }

    @GetMapping(API_ITEMS_URL)
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<ItemDTO>> getItems(
            @RequestParam(name = LIMIT_PARAMETER, defaultValue = "10") Integer limit,
            @RequestParam(name = OFFSET_PARAMETER, defaultValue = "0") Integer offset
    ) {
        List<ItemDTO> items = itemService.getItems(limit, offset);
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @GetMapping(API_ITEMS_WITH_ID_URL)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ItemDTO> getItem(
            @PathVariable(ID_VARIABLE) Long id
    ) {
        ItemDTO item = itemService.getItemById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(item, HttpStatus.OK);
    }

    @PostMapping(API_ITEMS_URL)
    @SuppressWarnings("unchecked")
    public ResponseEntity<ArticleDTO> saveItem(
            @RequestBody ItemDTO itemDTO, BindingResult bindingResult
    ) {
        itemValidator.validate(itemDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ItemDTO returningItem = itemService.add(itemDTO);
        return new ResponseEntity(returningItem, HttpStatus.CREATED);
    }

    @DeleteMapping(API_ITEMS_WITH_ID_URL)
    public ResponseEntity deleteItem(
            @PathVariable(ID_VARIABLE) Long id
    ) {
        ItemDTO item = itemService.deleteItem(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
