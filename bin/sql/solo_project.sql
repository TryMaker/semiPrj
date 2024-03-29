   (	"MCODE" NUMBER(4,0), 

	"MTITLE" VARCHAR2(60 BYTE) NOT NULL ENABLE, 

	"MMEMO" VARCHAR2(2000 BYTE), 

	"MDATE" DATE NOT NULL ENABLE, 

	"MBYTE" NUMBER(5,0) NOT NULL ENABLE, 

	 PRIMARY KEY ("MCODE"));
	 
	   CREATE TABLE "SCOTT"."PRO_TRANSACT" 

   (	"TCODE" NUMBER(4,0), 

	"TIO" VARCHAR2(4 BYTE) NOT NULL ENABLE, 

	"TAMOUNT" NUMBER(10,0) NOT NULL ENABLE, 

	"TDESCRIBE" VARCHAR2(20 BYTE) NOT NULL ENABLE, 

	"TDATE" DATE NOT NULL ENABLE, 

	 PRIMARY KEY ("TCODE"));
	 
	   CREATE TABLE "SCOTT"."PRO_WISH" 

   (	"WRANK" NUMBER(1,0), 

	"WNAME" VARCHAR2(20 BYTE) NOT NULL ENABLE, 

	"WCOST" VARCHAR2(20 BYTE) NOT NULL ENABLE, 

	"WDESCRIBE" VARCHAR2(20 BYTE) NOT NULL ENABLE, 

	 PRIMARY KEY ("WRANK"));