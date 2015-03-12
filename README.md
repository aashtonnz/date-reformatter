# date-reformat
A simple Java application for reformatting a stream of dates read from standard input.

If a line is in a recognised format and is a valid date then it is reformatted and printed, otherwise the line and a relevant error message is printed.

A line is in a recognised format 'day <sep> month <sep> year' if:
1. 'day' is a one or two digit integer,
2. 'month' is a one or two digit integer or the first three letters of a month in lower (e.g. jan), upper (e.g. JAN), or title case (e.g. Jan),
3. 'year' is a two or four digit integer, which stands for a year between 1950 and 2049 if the former is true, and
4. '<sep>' is a single space, a hyphen, or a slash.

A date is valid if:
1. it is between the years 1753 and 3000, and 
2. 2. the month and day are valid (noting leap years).

Dates are printed in the format 'dd <space> Mon <space> yyyy'. For example, '03-1-85' is printed as '3 Jan 1985'.
