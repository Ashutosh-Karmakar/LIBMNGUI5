package customer.librarymng_backend.handlers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.handler.EventHandler;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.handler.EventHandler;


import cds.gen.borrowservice.BorrowService_;
import cds.gen.booksservice.Books_;
import cds.gen.booksservice.Books;
import cds.gen.subscriberservice.Subscriber_;
import cds.gen.subscriberservice.Subscriber;
import cds.gen.borrowservice.Borrow;
import cds.gen.borrowservice.Borrow_;

@Component
@ServiceName(BorrowService_.CDS_NAME)
public class BorrowHandler implements EventHandler{
    @Autowired
        PersistenceService db;

    @Before(event = CqnService.EVENT_CREATE, entity = Borrow_.CDS_NAME)
    void beforeCreate(List<Borrow> borrows){
        for(Borrow borrow : borrows){
            String bookId = borrow.getBook();
            String subscriberId = borrow.getSubscriber();
            CqnSelect selectBook = Select.from(Books_.class).where(b -> b.ID().eq(bookId));
            Books book = db.run(selectBook).first(Books.class)
            .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Book doesnot exist"));
            CqnSelect selectSubs = Select.from(Subscriber_.class).where(b -> b.ID().eq(subscriberId));
            Subscriber subs = db.run(selectSubs).first(Subscriber.class)
            .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Subscriber doesnot exist"));
            if(book.getStock() < borrow.getQuantity()){
                throw new ServiceException(ErrorStatuses.NOT_FOUND, "Book is OUT OF STOCK");
            }
            int qty = borrow.getQuantity();
            book.setStock(book.getStock() - qty);
            CqnUpdate update = Update.entity(Books_.class).data(book).where(b -> b.ID().eq(bookId));
            db.run(update);

        }
    }
}