using { sap.capire.Bookstore as db } from '../db/schema';

service LibService{
    entity Books as projection on db.Books;
    entity Subscriber as projection on db.Subscriber;
    entity Borrow as projection on db.Borrow;
    entity BooksStock as projection on db.BookStock;
}