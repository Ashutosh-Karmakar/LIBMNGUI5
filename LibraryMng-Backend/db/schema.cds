namespace sap.capire.Bookstore;

using {Currency, cuid, managed, sap.common.CodeList} from '@sap/cds/common';

entity Books: cuid, managed{
    Title: String(100);
    Author: String(100);
    Genres: String(100);
}

entity Subscriber: cuid, managed{
    Name : String(100);
    Age: Integer;
    Location: String(200);
}

entity BooksStock: cuid, managed{
    Book : UUID;
    Quantity: Integer;
}

entity Borrow: cuid, managed{
    Book: UUID;
    Subscriber: UUID;
    Quantity: Integer;
}