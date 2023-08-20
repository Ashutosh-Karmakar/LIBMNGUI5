using { sap.capire.Bookstore as db } from '../db/schema';

service BooksService{
    entity Books as projection on db.Books;
    entity BooksStock as projection on db.BooksStock;
}

service SubscriberService{
    entity Subscriber as projection on db.Subscriber;
}

service BorrowService{
    entity Borrow as projection on db.Borrow;
}

