for i in `cat terr_geo_ids.csv`
do
  for j in `cat mstatus_ids.csv`
  do
    for k in `cat gender_ids.csv`
    do
        echo $i,$j,$k
    done
  done
done
