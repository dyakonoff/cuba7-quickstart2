-- begin STOCKMAN_STOCK_ITEM
alter table STOCKMAN_STOCK_ITEM add constraint FK_STOCKMAN_STOCK_ITEM_ON_PRODUCT foreign key (PRODUCT_ID) references STOCKMAN_PRODUCT(ID)^
create unique index IDX_STOCKMAN_STOCK_ITEM_UNQ on STOCKMAN_STOCK_ITEM (PRODUCT_ID) ^
create index IDX_STOCKMAN_STOCK_ITEM_ON_PRODUCT on STOCKMAN_STOCK_ITEM (PRODUCT_ID)^
-- end STOCKMAN_STOCK_ITEM