USE [MEMBER]
GO
/****** Object:  Table [dbo].[PRECREDIT]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PRECREDIT](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MEMBER_ID] [int] NULL,
	[ID_ID] [int] NULL,
	[CC_ID] [int] NULL,
	[BILL_ID] [int] NULL,
	[BMB_ID] [int] NULL,
	[CRL] [smallint] NULL,
	[CREATE_TIME] [smalldatetime] NULL,
	[IMAGE_BILL] [varchar](255) NULL,
 CONSTRAINT [PK_PRECREDIT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MEMBER]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MEMBER](
	[ID] [int] IDENTITY(0,1) NOT NULL,
	[ORG_FLAG] [tinyint] NULL,
	[USER_NAME] [varchar](50) NULL,
	[PASSWORD] [varchar](20) NULL,
	[NAME] [varchar](20) NULL,
	[MOBILE] [varchar](12) NULL,
	[EMAIL] [varchar](50) NULL,
	[EDUCATION] [tinyint] NULL,
	[INDUSTRY] [tinyint] NULL,
	[WC_NO] [varchar](50) NULL,
	[WC_USER_NAME] [varchar](50) NULL,
	[WC_PROVINCE] [varchar](50) NULL,
	[WC_CITY] [varchar](50) NULL,
	[WC_SIGNATURE] [varchar](60) NULL,
	[TYPE] [tinyint] NULL,
	[CRL] [smallint] NULL,
	[CRL_AVL] [smallint] NULL,
	[LAST_APPL_NO] [int] NULL,
	[LAST_SCORE] [int] NULL,
	[LAST_RATING] [varchar](1) NULL,
	[LAST_DECISION] [tinyint] NULL,
	[LAST_REASON_1] [varchar](4) NULL,
	[LAST_REASON_2] [varchar](4) NULL,
	[LAST_REASON_3] [varchar](4) NULL,
	[LAST_PBOC_BACK_TIME] [smalldatetime] NULL,
	[POINTS] [smallint] NULL,
	[STATUS] [tinyint] NULL,
	[BLOCK_CODE] [varchar](1) NULL,
	[CREATE_TIME] [smalldatetime] NULL,
 CONSTRAINT [PK_MEMBER] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-未知
1-初中及以下
2-高中、中专
3-大专
4-本科
5-硕士
6-博士
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MEMBER', @level2type=N'COLUMN',@level2name=N'EDUCATION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-未知
1-政府机关、社会团体
2-军事、公检法
3-学校、医院
4-专业事务所
5-信息通信、IT互联网
6-金融业
7-交通运输
8-公共事业
9-能源矿产
10-商业零售、内外贸易
11-房地产、建筑业
12-加工、制造业
13-餐饮、酒店、旅游
14-服务、咨询
15-媒体、体育、娱乐
16-农林牧渔
17-网店店主
18-学生
19-自由职业者
20-其他' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MEMBER', @level2type=N'COLUMN',@level2name=N'INDUSTRY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-借款人 1-投资人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MEMBER', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-正常 1-客户销户 2-拒绝往来' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MEMBER', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
/****** Object:  Table [dbo].[ID_CARD]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ID_CARD](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MEMBER_ID] [int] NULL,
	[ID_NO] [varchar](18) NULL,
	[SEX] [tinyint] NULL,
	[BIRTHDAY] [smalldatetime] NULL,
	[NATIONALITY] [varchar](8) NULL,
	[ADDRESS] [varchar](100) NULL,
	[ISSUER] [varchar](50) NULL,
	[VALID_FROM] [smalldatetime] NULL,
	[VALID_THRU] [smalldatetime] NULL,
	[PROVINCE] [varchar](20) NULL,
	[CITY] [varchar](20) NULL,
	[IMAGE_FRONT] [varchar](100) NULL,
	[IMAGE_BACK] [varchar](100) NULL,
	[APPL_NO] [varchar](16) NULL,
	[CREATE_TIME] [smalldatetime] NULL,
 CONSTRAINT [PK_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-男 1-女' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ID_CARD', @level2type=N'COLUMN',@level2name=N'SEX'
GO
/****** Object:  Table [dbo].[CREDITCARD]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CREDITCARD](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MEMBER_ID] [int] NULL,
	[BANK] [smallint] NULL,
	[CARD_NO] [varchar](19) NULL,
	[TYPE] [tinyint] NULL,
	[NAME] [varchar](20) NULL,
	[VALID_FROM] [smalldatetime] NULL,
	[VALID_THRU] [smalldatetime] NULL,
	[NAME_ENG] [varchar](20) NULL,
	[IMAGE] [varchar](100) NULL,
	[APPL_NO] [varchar](16) NULL,
	[CREATE_TIME] [smalldatetime] NULL,
 CONSTRAINT [PK_CREDITCARD] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-普卡 1-金卡 2-白金卡以上' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CREDITCARD', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
/****** Object:  Table [dbo].[BILL_MAILBOX]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BILL_MAILBOX](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MEMBER_ID] [int] NULL,
	[EMAIL] [varchar](50) NULL,
	[PASSWORD] [varchar](20) NULL,
	[APPL_NO] [varchar](16) NULL,
	[STATUS] [tinyint] NULL,
	[CREATE_TIME] [smalldatetime] NULL,
 CONSTRAINT [PK_BILL_MAILBOX] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-有效 1-失效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BILL_MAILBOX', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
/****** Object:  Table [dbo].[BILL]    Script Date: 03/21/2014 18:25:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BILL](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MEMBER_ID] [int] NULL,
	[BANK] [smallint] NULL,
	[TYPE] [tinyint] NULL,
	[CRL] [smallint] NULL,
	[PAY_DUE] [smalldatetime] NULL,
	[AMT_RMB] [float] NULL,
	[AMT_USD] [float] NULL,
	[CYCLE_FROM] [smalldatetime] NULL,
	[CYCLE_THRU] [smalldatetime] NULL,
	[EMAIL] [varchar](50) NULL,
	[IMAGE] [varchar](100) NULL,
	[APPL_NO] [varchar](16) NULL,
	[CREATE_TIME] [smalldatetime] NULL,
 CONSTRAINT [PK_BILL] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'调用parameter.bank' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BILL', @level2type=N'COLUMN',@level2name=N'BANK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-拍摄 1-爬虫' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BILL', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
