-- begin STOCKMAN_PRODUCT
create table STOCKMAN_PRODUCT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRICE decimal(19, 2) not null,
    DESCRIPTION longvarchar,
    --
    primary key (ID)
)^
-- end STOCKMAN_PRODUCT
-- begin STOCKMAN_STOCK_ITEM
create table STOCKMAN_STOCK_ITEM (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID varchar(36) not null,
    QUANTITY decimal not null,
    --
    primary key (ID)
)^
-- end STOCKMAN_STOCK_ITEM
