SELECT
  w.sitename                                                                     AS siteName,
  m.hostname                                                                     AS username,
  m.hostemail                                                                    AS hostName,
  decode(c.has_data_meeting, 'N', c.pure_audio_conf_id, c.meeting_id)            AS confId,
  m.confname                                                                     AS confName,
  decode(p.private_talk_starttime, NULL, p.start_time, p.private_talk_starttime) AS startTime,
  decode(p.end_time, NULL, p.private_talk_endtime, p.end_time)                   AS endTime,
  p.Orig_Country_Code                                                            AS orignCountryCode,
  p.Orign_Area_Code                                                              AS originAreaCode,
  p.orign_number                                                                 AS origNumber,
  p.dest_country_code                                                            AS destCountryCode,
  p.dest_area_code                                                               AS destAreaCode,
  p.phone_number                                                                 AS destNumber,
  p.access_number                                                                AS accessNumber,
  decode(p.is_dialout, 'N', 1, 0)                                                AS callIn,
  p.calltype                                                                     AS callType
FROM
  cdr c,
  cdr_participants p,
  xxrpt_hgsmeetingreport m,
  wbxsite w
WHERE
  c.cdr_id = p.cdr_id
  AND c.site_id = m.siteid
  AND w.siteid = m.siteid
  AND decode(c.has_data_meeting, 'N', c.pure_audio_conf_id, c.meeting_id) = m.confid
  AND p.orign_number <> 'mmp_video'
  AND p.phone_number IS NOT NULL
  AND m.webexid <> 'wbxadmin'
  AND c.end_time + 8 / 24 BETWEEN :startTime AND :endTime
  AND w.sitename IN (:siteNames)
ORDER BY p.start_time ASC