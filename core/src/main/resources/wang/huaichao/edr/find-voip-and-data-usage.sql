SELECT
  m.hostname  AS username,
  m.hostemail AS hostname,
  m.confid,
  m.confname,
  m.siteName,
  a.starttime,
  a.endtime,
  'DATA'      AS callType
FROM
  xxrpt_hgsmeetingreport m,
  XXRPT_HGSMEETINGUSERREPORT a
WHERE
  a.siteid = m.siteid
  AND a.confid = m.confid
  AND m.sitename IN (:siteNames)
  AND m.endtime + 8 / 24 BETWEEN :startTime AND :endTime
  AND m.webexid <> 'wbxadmin'

UNION

SELECT
  m.hostemail AS hostname,
  m.confid,
  m.confname,
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