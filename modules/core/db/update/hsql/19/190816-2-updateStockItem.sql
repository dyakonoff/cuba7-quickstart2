alter table STOCKMAN_STOCK_ITEM alter column QUANTITY rename to QUANTITY__U28585 ^
alter table STOCKMAN_STOCK_ITEM alter column QUANTITY__U28585 set null ;
alter table STOCKMAN_STOCK_ITEM add column QUANTITY integer ^
update STOCKMAN_STOCK_ITEM set QUANTITY = 0 where QUANTITY is null ;
alter table STOCKMAN_STOCK_ITEM alter column QUANTITY set not null ;
