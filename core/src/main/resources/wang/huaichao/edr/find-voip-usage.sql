SELECT
  m.hostname  AS username,
  m.hostemail AS hostname,
  m.confid,
  m.confname,
  m.siteName,
  e.starttime,
  e.endtime,
  'VOIP'      AS callType
FROM
  XXRPT_HGSMEETINGREPORT m,
  WBXEVENTLOGUSER e
WHERE
  e.siteid = m.siteid
  AND e.confid = m.confid
  AND e.sessiontype IN (22, 12)
  AND m.sitename IN (:siteNames)
  AND m.endtime + 8 / 24 BETWEEN :startTime AND :endTime
  AND m.webexid <> 'wbxadmin'