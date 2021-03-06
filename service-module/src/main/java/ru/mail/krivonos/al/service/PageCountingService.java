package ru.mail.krivonos.al.service;

public interface PageCountingService {

    int getCountOfPages(int itemsNumber, int pageLimit);

    int getOffset(int pageNumber, int limit);

    int getCurrentPageNumber(int pageNumber, int countOfPages);
}
