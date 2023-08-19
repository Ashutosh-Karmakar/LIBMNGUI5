package customer.librarymng_backend.handlers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.libservice.BooksStock_;
import cds.gen.libservice.BooksStock;
import cds.gen.libservice.Books_;
import cds.gen.libservice.Books;

@Component
@ServiceName(BooksStock_.CDS_NAME)
public class BooksStockHandler {
    @Autowired
    PersistenceService db;

    @Before(event = CqnService.EVENT_CREATE, entity = BooksStock_.CDS_NAME)
    public void onCreate(List<BooksStock> booksStock){
        // UUID bookId = booksStock.getBook();
        sysout("hello");
        System.out.println(booksStock);
        // CqnSelect select = Select.from(Books_.class).where(b -> b.Book.eq(bookId));
        // Books book = db.run(select).first(Books.class)
        // .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Book doesnot exist"));
    }
    
}
