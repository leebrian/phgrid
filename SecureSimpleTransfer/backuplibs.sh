#!/bin/sh

if [ ! -d globus_backup_libs ]; then mkdir globus_backup_libs;fi

cd globus_backup_libs
cp $GLOBUS_LOCATION/lib/globus_wsrf_mds_aggregator.jar .
cp $GLOBUS_LOCATION/lib/globus_wsrf_mds_aggregator_stubs.jar .
cp $GLOBUS_LOCATION/lib/globus_wsrf_servicegroup.jar .
cp $GLOBUS_LOCATION/lib/globus_wsrf_servicegroup_stubs.jar .
