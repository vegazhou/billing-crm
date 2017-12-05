var Systemuser = {
    dataTable: null,
    options: "",
    row_count: 0,
    orgservicelist: null,
    allorgservicelist: null,
    deletedorgservicelist: null,
    userservicelist: [],
    init: function () {
        var self = this;
        pageSetUp();
        setting.sAjaxSource = G_CTX_ROOT + "/v1/sitereport/query";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle": "站点名称",
                "mData": "siteName",
                "bSortable": true,
                "sClass": "center",
                "render": function (data) {
                    return htmlencode(data);
                }
            },

            {
                "sTitle": "归属客户",
                "mData": "customer_NAME",
                "bSortable": true,
                "sClass": "center",
                "render": function (data) {
                    return htmlencode(data);
                }
            },

            {
                "sTitle": "状态",
                "mData": "state",
                "bSortable": false,
                "sClass": "center",
                "render": function (data) {
                    return htmlencode(data);
                }
            },

            {
                "sTitle": "操作",
                "mData": "f6",
                "bSortable": false,
                "sClass": "center",
                "sWidth": "25%",
                "render": function (data, dis, obj) {
                    var buttons = "";
                    contactDom = "<span class='btn btn-success btn-xs btn-detail'>客户联系人</span> ";
                    viewDom = "<span class='btn btn-success btn-xs btn-detail'>查看站点设置</span> ";
                    editDom = "<span class='btn btn-success btn-xs btn-edit'>修改站点设置</span> ";
                    customerDom = "<span class='btn btn-success btn-xs btn-customer'> 查看关联订单 </span> ";

                    return contactDom + viewDom + editDom + customerDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
                }
            }
        ];
        setting.aaSorting = [[0, "desc"]];
        setting.bStateSave = false;
        Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
        $("#datatable_tabletools_filter").html("");
        Systemuser.getallservices();
    },
    bindButtonEvent: function () {
        console.log("binlding");

        $(".btn-save").unbind("click").click(function () {
            console.log("9999---");
            var modulesArray = new Array();
            if (!validator.validate()) {
                return false;
            }


            /*
             $(".select-role").find("option").each(function () {
             modulesArray.push($(this).val());
             });
             if (modulesArray.length == 0) {
             $("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
             return false;
             }*/
            $(".btn-save").attr("disabled", true);
            var business = {};
            business.siteName = $.trim($('#sname').val());
            business.timeZone = $.trim($('#s_sserviceid').val());
            business.primaryLanguage = $.trim($('#s_languageid').val());
            business.timeZone = $.trim($('#s_sserviceid').val());
            business.contractId = Systemuser.contractId;
            business.location = $.trim($('#s_location').val());

            if ($('.SIMPLIFIED_CHINESE').prop("checked")) {
                modulesArray.push("SIMPLIFIED_CHINESE");
            }
            if ($('.TRADITIONAL_CHINESE').prop("checked")) {
                modulesArray.push("TRADITIONAL_CHINESE");
            }
            if ($('.ENGLISH').prop("checked")) {
                modulesArray.push("ENGLISH");
            }
            if ($('.JAPANESE').prop("checked")) {
                modulesArray.push("JAPANESE");
            }
            if ($('.KOREAN').prop("checked")) {
                modulesArray.push("KOREAN");
            }
            if ($('.FRENCH').prop("checked")) {
                modulesArray.push("FRENCH");
            }
            if ($('.GERMAN').prop("checked")) {
                modulesArray.push("GERMAN");
            }
            if ($('.ITALIAN').prop("checked")) {
                modulesArray.push("ITALIAN");
            }
            if ($('.SPANISH').prop("checked")) {
                modulesArray.push("SPANISH");
            }
            if ($('.SPANISH_CASTILLA').prop("checked")) {
                modulesArray.push("SPANISH_CASTILLA");
            }
            if ($('.SWEDISH').prop("checked")) {
                modulesArray.push("SWEDISH");
            }
            if ($('.PORTUGUESE').prop("checked")) {
                modulesArray.push("PORTUGUESE");
            }
            if ($('.RUSSIAN').prop("checked")) {
                modulesArray.push("RUSSIAN");
            }
            if ($('.TURKEY').prop("checked")) {
                modulesArray.push("TURKEY");
            }
            if ($('.HOLLAND').prop("checked")) {
                modulesArray.push("HOLLAND");
            }
            if ($('.DANISH').prop("checked")) {
                modulesArray.push("DANISH");
            }
            business.additionalLanguages = modulesArray;
            if ($.trim($('#edit_userid').val()) == "") {

                var postURL = G_CTX_ROOT + "/v1/wbxsite/";
                var update = false;
                var calltype = "post";
            } else {

                var postURL = G_CTX_ROOT + "/v1/sitereport/" + $.trim($('#edit_userid').val());
                var update = false;
                var calltype = "put";

            }
            var postData = JSON.stringify(business);
            $.ajaxInvoke({
                url: postURL,
                type: calltype,
                data: postData,
                success: function (data) {

                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");

                    msgBox('success', "站点修改成功");
                    Systemuser.refresh();
                },
                error: function (data) {

                    if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_contract_allowed") {
                        $(".btn-save").attr("disabled", false);
                        validator.clearForm();
                        $("#edit_userid").val("");
                        $("#myModal").modal("hide");
                        $('#sname').val("");

                        msgBox('fail', "只有草稿合同可以增加站点！");
                        Systemuser.dataTable.fnDraw();
                        return;
                    }

                    if (data.status == "400" && JSON.parse(data.responseText).error.key == "user.already.existed") {
                        $(".btn-save").attr("disabled", false);
                        $("#sadmin").nextAll().filter("span").html("<font color=red >管理员账号已经被使用！</font>");
                        return;
                    }
                    if (data.status == "400" && JSON.parse(data.responseText).error.key == "duplicated.crm.cutomer.id") {
                        $(".btn-save").attr("disabled", false);
                        $("#scrmcustomerid").nextAll().filter("span").html("<font color=red >CRMCustomerID已经被使用！</font>");
                        return;
                    }


                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");

                    msgBox('success', "站点增加成功");
                    Systemuser.refresh();
                },
            });
        });

        $(".btn-customer").unbind("click").click(function () {
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = "";
            window.location.href = '../report/onesitereport.jsp?id=' + $(this).parents("TR").find("input[type=hidden][name=sysId]").val() + "&name=" + uName;

        });


        $(".btn-detail").unbind("click").click(function () {

            //validator.clearForm();
            $("#sname").nextAll().filter("span").html("");
            $(".btn-save").attr("disabled", false);
            var parentTR = $(this).parents("TR");
            var userid = parentTR.find("input[type=hidden][name=sysId]").val()
            $("#edit_userid").val(userid);

            //$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
            //$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
            Systemuser.getUserDetail(userid, true);
            $("#myModalLabel").html("站点信息");
            $('#sname').val("").prop("disabled", true);
            $("#edit_username").val("");
            $("input[type='checkbox']").prop("checked", false);
            $("#s_languageid").val("");
            $("#s_sserviceid").val("");
            $("#s_location").val("");
            $("#storageCapacity").val("");
            $('.scLicenseModel').hide();
            $('.ecLicenseModel').hide();
            $('.tcLicenseModel').hide();
            $('.mcLicenseModel').hide();
            $('.eeLicenseModel').hide();
            $("input[type='text']").val("");
            $(".btn-save").hide();
            $("#myModal").modal("show");
        });


        $(".btn-edit").unbind("click").click(function () {

            validator.clearForm();
            $("#sname").nextAll().filter("span").html("");
            $(".btn-save").attr("disabled", false);
            var parentTR = $(this).parents("TR");
            var userid = parentTR.find("input[type=hidden][name=sysId]").val()
            $("#edit_userid").val(userid);

            //$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
            //$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
            Systemuser.getUserDetail(userid, false);
            $("#myModalLabel").html("站点修改");
            $('#sname').val("").prop("disabled", true);
            $("#edit_username").val("");
            $("input[type='checkbox']").prop("checked", false);
            $("#s_languageid").val("");
            $("#s_sserviceid").val("");
            $("#s_location").val("");
            $("#storageCapacity").val("");
            $('.scLicenseModel').hide();
            $('.ecLicenseModel').hide();
            $('.tcLicenseModel').hide();
            $('.mcLicenseModel').hide();
            $('.eeLicenseModel').hide();
            $('.basicconfig').hide();
            $("input[type='text']").val("");
            $(".btn-save").show();
            $("#myModal").modal("show");
        });


        $(".btn-contract").unbind("click").click(function () {
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
            window.location.href = '../order/order.jsp?id=' + $(this).parents("TR").find("input[type=hidden][name=sysId]").val() + "&name=" + encodeURIComponent(uName);

        });


        $(".btn-search").unbind("click").click(function () {
            Systemuser.dataTable.fnDraw();
        });

        $("#s_sserviceid").unbind("change").change(function () {

            Systemuser.refresh();

        });


        //$(".btn-save").unbind("click").click(function () {
        //	console.log("9999---");
        //	var modulesArray = new Array();
        //	var deletesArray = new Array();
        //	if (!validator.validate()) {
        //		return false;
        //	}
        //
        //
        //
        //	/*
        //	$(".select-role").find("option").each(function () {
        //		modulesArray.push($(this).val());
        //	});
        //	if (modulesArray.length == 0) {
        //		$("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
        //		return false;
        //	}*/
        //	$(".btn-save").attr("disabled", true);
        //
        //
        //});
    },


    getContractInfo: function (contractId) {
        $.ajaxInvoke({
            url: G_CTX_ROOT + "/v1/contract/" + contractId,
            data: "",
            type: "get",
            success: function (data) {
                console.log("----", data);
                $("#sname").val(data.displayName);


                //$("#sadmin").prop("disabled", true);


            },
        });
    },


    getUserDetail: function (userid, notedit) {
        $.ajaxInvoke({
            url: G_CTX_ROOT + "/v1/wbxsite/" + userid,
            data: "",
            type: "get",
            success: function (data) {
                console.log("----", data);
                $(".mc5sitesetion").hide();
                if (data.pid.indexOf("5260") >= 0 && data.pid.length == 9) {
                    $(".mc5sitesetion").show();
                }
                $("#sname").val(data.siteName);
                $("#edit_username").val(data.siteName);
                $("#s_languageid").val(data.primaryLanguage);
                $("#s_sserviceid").val(data.timeZone);
                $("#s_location").val(data.location);
                $("#storageCapacity").val(data.storageCapacity);
                if (data.callBack == 1) {
                    $('.callBack').prop("checked", true);
                }
                if (data.audioBroadCast == 1) {
                    $('.audioBroadCast').prop("checked", true);
                }
                if (data.internationalCallBack == 1) {
                    $('.internationalCallBack').prop("checked", true);
                }
                if (data.tollCallIn == 1) {
                    $('.tollCallIn').prop("checked", true);
                }
                if (data.tollFreeCallIn == 1) {
                    $('.tollFreeCallIn').prop("checked", true);
                }

                if (data.globalCallIn == 1) {
                    $('.globalCallIn').prop("checked", true);
                }

                if (data.cloudConnectedAudio == 1) {
                    $('.cloudConnectedAudio').prop("checked", true);
                }

                if (data.voip == 1) {
                    $('.voip').prop("checked", true);
                }

                if (data.sipInOut == 1) {
                    $('.sipInOut').prop("checked", true);
                }
                if (notedit) {
                    if (data.mcLicenseModel != null) {
                        $('.mcLicenseModel').show();
                        if (data.mcLicenseOverage == 1) {
                            $('.mcLicenseOverage').prop("checked", true);
                        }

                        if (data.mcAttendeeOverage == 1) {
                            $('.mcAttendeeOverage').prop("checked", true);
                        }
                        $("#mcLicenseVolume").val(data.mcLicenseVolume);
                        $("#mcAttendeeCapacity").val(data.mcAttendeeCapacity);

                    }

                    if (data.scLicenseModel != null) {
                        $('.scLicenseModel').show();
                        if (data.scLicenseOverage == 1) {
                            $('.scLicenseOverage').prop("checked", true);
                        }

                        if (data.scAttendeeOverage == 1) {
                            $('.scAttendeeOverage').prop("checked", true);
                        }
                        $("#scLicenseVolume").val(data.scLicenseVolume);
                        $("#scAttendeeCapacity").val(data.scAttendeeCapacity);

                    }

                    if (data.tcLicenseModel != null) {
                        $('.tcLicenseModel').show();
                        if (data.tcLicenseOverage == 1) {
                            $('.tcLicenseOverage').prop("checked", true);
                        }

                        if (data.tcAttendeeOverage == 1) {
                            $('.tcAttendeeOverage').prop("checked", true);
                        }
                        $("#tcLicenseVolume").val(data.tcLicenseVolume);
                        $("#tcAttendeeCapacity").val(data.tcAttendeeCapacity);

                    }

                    if (data.ecLicenseModel != null) {
                        $('.ecLicenseModel').show();
                        if (data.ecLicenseOverage == 1) {
                            $('.ecLicenseOverage').prop("checked", true);
                        }

                        if (data.ecAttendeeOverage == 1) {
                            $('.ecAttendeeOverage').prop("checked", true);
                        }
                        $("#ecLicenseVolume").val(data.ecLicenseVolume);
                        $("#ecAttendeeCapacity").val(data.ecAttendeeCapacity);

                    }

                    if (data.eeLicenseModel != null) {
                        $('.eeLicenseModel').show();
                        if (data.eeLicenseOverage == 1) {
                            $('.eeLicenseOverage').prop("checked", true);
                        }

                        if (data.eeAttendeeOverage == 1) {
                            $('.eeAttendeeOverage').prop("checked", true);
                        }
                        $("#eeLicenseVolume").val(data.eeLicenseVolume);
                        $("#eeAttendeeCapacity").val(data.eeAttendeeCapacity);

                    }
                }
                //$("#sadmin").prop("disabled", true);
                languages = data.additionalLanguage.split(";");
                $.each(languages, function (key, value) {
                    if (value == "SIMPLIFIED_CHINESE") {
                        $('.SIMPLIFIED_CHINESE').prop("checked", true);
                    }
                    if (value == "TRADITIONAL_CHINESE") {
                        $('.TRADITIONAL_CHINESE').prop("checked", true);
                    }
                    if (value == "ENGLISH") {
                        $('.ENGLISH').prop("checked", true);
                    }
                    if (value == "JAPANESE") {
                        $('.JAPANESE').prop("checked", true);
                    }
                    if (value == "KOREAN") {
                        $('.KOREAN').prop("checked", true);
                    }
                    if (value == "FRENCH") {
                        $('.FRENCH').prop("checked", true);
                    }
                    if (value == "GERMAN") {
                        $('.GERMAN').prop("checked", true);
                    }
                    if (value == "ITALIAN") {
                        $('.ITALIAN').prop("checked", true);
                    }
                    if (value == "SPANISH") {
                        $('.SPANISH').prop("checked", true);
                    }
                    if (value == "SPANISH_CASTILLA") {
                        $('.SPANISH_CASTILLA').prop("checked", true);
                    }
                    if (value == "SWEDISH") {
                        $('.SWEDISH').prop("checked", true);
                    }
                    if (value == "HOLLAND") {
                        $('.HOLLAND').prop("checked", true);
                    }
                    if (value == "PORTUGUESE") {
                        $('.PORTUGUESE').prop("checked", true);
                    }
                    if (value == "RUSSIAN") {
                        $('.RUSSIAN').prop("checked", true);
                    }
                    if (value == "TURKEY") {
                        $('.TURKEY').prop("checked", true);
                    }
                    if (value == "DANISH") {
                        $('.DANISH').prop("checked", true);
                    }


                });


            },
        });
    },

    getallservices: function () {


        console.log("here99------------");
        $.ajaxInvoke({
            url: G_CTX_ROOT + "/v1/timezone/query",
            data: "",
            type: "get",
            success: function (returnData) {
                //returnData= JSON.parse(returnData);


                $.each(returnData, function (key, value) {
                    Systemuser.options += '<option value="' + value.value + '">' + value.displayName + '</option>';


                });

                $('#s_sserviceid').html(Systemuser.options);

            },

        });


    },

    refresh: function () {
        Systemuser.dataTable.fnDraw();

    },


    bindLaterEvent: function () {
        console.log("bindinglater");
        $("#s_sserviceid1").keydown(function(e){
            var key = e.which;
            if (key === 13) {
                console.log('HELLO ENTER');
                $('#btn-search').trigger("click");
                return false;
            }
        });

    }


}

$(document).ready(Systemuser.init());
