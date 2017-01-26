for i in `cat geo_ids.csv`
do
  for j in `cat mstatus_ids.csv`
  do
    for k in `cat gender_ids.csv`
    do
      for l in `cat home_ids.csv`
      do
        echo $i,$j,$k,$l
      done
    done
  done
done
rm tmp_geo.csv
