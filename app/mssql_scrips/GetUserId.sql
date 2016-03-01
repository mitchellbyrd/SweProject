if objectproperty(object_id('dbo.GetUserId'), N'IsProcedure') = 1
	drop procedure dbo.GetUserId
go

create procedure dbo.GetUserId
	@emailAddress varchar(64)
,	@password varchar(64)
as 
	set nocount on

	select 
		u.Id
	from 
		dbo.Users u
	where
		u.EmailAddress = @emailAddress
		and u.Password = @password

go